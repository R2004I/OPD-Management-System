package com.pms.easy_book.service;

import com.google.zxing.WriterException;
import com.pms.easy_book.Enum.Status;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.entity.PaymentOrder;
import com.pms.easy_book.repo.AppointmentRepo;
import com.pms.easy_book.repo.PaymentRepo;
import com.pms.easy_book.utils.RazorpayUtil;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private AppointmentService service;


    //make order
    @Transactional
    public String createOrder(Long appointmentId)throws RazorpayException {
        Appointments appointments = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("appointment not found"));

        System.out.println("I am here");

        RazorpayClient razorpay=new RazorpayClient(razorpayKey,razorpaySecret);
        //Json
        JSONObject options=new JSONObject();
        options.put("amount",(service.calculateTotalPrice(appointmentId) * 100)); //paise
        options.put("currency","INR");
        options.put("receipt","appointment"+ appointmentId);
        options.put("payment_capture",1);

        Order order=razorpay.orders.create(options);
        System.out.println(order.toString());
        System.out.println(appointments.getAmount()*100);

        PaymentOrder payment = new PaymentOrder();

        payment.setAmount(appointments.getAmount());
        payment.setOrderId(order.get("id"));
        payment.setPaymentId(null);
        payment.setStatus(Status.PENDING);

        payment.setAppointments(appointments);
        appointments.setPayment(payment);

        paymentRepo.save(payment);
        appointmentRepo.save(appointments);
        return order.get("id");
    }


    public boolean verifyPayment(String orderId, String paymentId, String razorpaySignature){
        String payload = orderId + '|' + paymentId;
        try {
            return RazorpayUtil.verifySignature(payload, razorpaySignature, razorpaySecret);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Transactional
    public String capturePayment(String razorpayPaymentId, String razorpayOrderId, String razorpaySignature){
        PaymentOrder payment = paymentRepo.findByOrderId(razorpayOrderId);
        if(payment == null){
            throw new RuntimeException("payment not found");
        }
        if(verifyPayment(razorpayOrderId,razorpayPaymentId,razorpaySignature)){

            //payment.getAppointments().setStatus(Status.SUCCESSFUL);
            payment.setPaymentId(razorpayPaymentId);
            payment.setStatus(Status.SUCCESSFUL);
            payment.setCreatedAt(LocalDateTime.now());

            payment.getAppointments().setConfirmationCode(generateBookingCode());
            paymentRepo.save(payment);
            appointmentRepo.save(payment.getAppointments());

            try {
                emailService.confirmCode(payment.getAppointments());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }
        return payment.getAppointments().getConfirmationCode();
    }

    public static String generateBookingCode() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return uuid.toUpperCase(); // Optional: Convert to uppercase
    }


}
