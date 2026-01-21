package com.pms.easy_book.controller.authenticated_controller;


import com.pms.easy_book.dto.AppointmentDto;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authenticated")
public class AppointmentControllerAuth {

    @Autowired
    private AppointmentService service;


    @PostMapping("/create-new-appointment")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDto dto,
                                               @RequestParam("doctorId") Long doctorId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Appointments appointment = service.createAppointment(dto, doctorId, userEmail);
        Map<String, Object> response = new HashMap<>();
        response.put("appointmentId", appointment.getId());
        response.put("patientName", appointment.getPatientName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelAppointment(@RequestParam("id") Long appointmentId)
    {
        String s = service.cancelAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @GetMapping("/details")
    public ResponseEntity<Appointments> getAppointmentDetails(@RequestParam("code") String code)
    {
        Appointments appointmentDetails = service.getAppointmentDetails(code);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDetails);
    }
}
