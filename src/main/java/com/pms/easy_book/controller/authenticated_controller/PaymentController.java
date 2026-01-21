package com.pms.easy_book.controller.authenticated_controller;

import com.pms.easy_book.dto.PaymentDTO;
import com.pms.easy_book.service.AppointmentService;
import com.pms.easy_book.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authenticated/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AppointmentService service;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestParam("id") Long appointmentId)
    {
        try{
            String orderId = paymentService.createOrder(appointmentId);
            int amount = service.calculateTotalPrice(appointmentId);
            Map<String, Object> response = new HashMap<>();
            response.put("order_id", orderId); // ✅ Use "order_id" instead of "orderId"
            response.put("amount", (amount * 100)); // ✅ Ensure integer paise value
            response.put("currency", "INR");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order");
        }

    }

    @PostMapping("/capture-payment")
    public ResponseEntity<?> updateOrderStatus(@RequestBody PaymentDTO dto)
    {
        String confirmationCode = paymentService.capturePayment(dto.getRazorpayPaymentId(), dto.getRazorpayOrderId(), dto.getRazorpaySignature());
        Map<String,String> response = new HashMap<>();
        response.put("confirmationCode",confirmationCode);
        // System.out.println("Email sent successfully...");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
