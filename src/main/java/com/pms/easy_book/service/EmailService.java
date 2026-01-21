package com.pms.easy_book.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.utils.QRCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Appointments appointment)
    {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(appointment.getPatientEmail());
        message.setSubject("✅ Appointment Booked ");


        message.setText("Hi " + appointment.getPatientName() + ",\n\n" +
                "Thank you for booking your appointment " +
                "with us.\n\n" +
                "We have successfully received your details and confirmed your consultation.\n\n" +
                "Here is all your details: \n\n"+
                "Patient name : "+appointment.getPatientName()+"\n"+
                "Patient email : "+appointment.getPatientEmail()+"\n"+
                "Patient phone number : "+appointment.getPatientPhoneNo()+"\n"+
                "Appointment Code : "+appointment.getConfirmationCode()+"\n"+
                "Total paid amount : "+appointment.getAmount()+"\n"+
                "Doctor's name : "+appointment.getDoctor().getName()+"\n"+
                "Checkup date : "+appointment.getAppointmentDate()+"\n"+
                "📌 If you have any questions or need to reschedule, feel free to contact our support team.\n\n" +
                "We look forward to assisting you with your healthcare needs.\n\n" +
                "Warm regards,\n" +
                "The HEALTH HUB Team");


        mailSender.send(message);
    }

    public void sendAppointmentQRCode(String to, String subject, String text, byte[] qrCodeImage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        InputStreamSource attachment = new ByteArrayResource(qrCodeImage);
        helper.addAttachment("appointment_qr.png", attachment);

        mailSender.send(message);
    }

    public void confirmCode(Appointments appointment) throws NoSuchAlgorithmException, IOException, WriterException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Prepare data to embed in QR
        Map<String, String> qrData = new HashMap<>();
        qrData.put("appointmentId", appointment.getId().toString());
        qrData.put("patientName", appointment.getPatientName());
        qrData.put("date", appointment.getAppointmentDate().toString());
        qrData.put("confirmationCode", appointment.getConfirmationCode());

        // Generate secure hash
        String secureHash = generateSecureHash(appointment.getId() + appointment.getConfirmationCode() + "SECRET_KEY");
        qrData.put("secureHash", secureHash);

        // Convert data to JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(qrData);

        // Generate QR code
        byte[] qrCodeImage = QRCodeGenerator.generateQRCode(jsonData, 300, 300);

        helper.setTo(appointment.getPatientEmail());
        helper.setSubject("Your Appointment Confirmation");
        helper.setText("Dear " + appointment.getPatientName() + ",\n\nHere is your QR code for the appointment."+
                qrCodeImage);

        InputStreamSource attachment = new ByteArrayResource(qrCodeImage);
        helper.addAttachment("appointment_qr.png", attachment);
        mailSender.send(message);
    }

    private String generateSecureHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
