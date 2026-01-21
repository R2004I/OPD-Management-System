package com.pms.easy_book.controller.admin_controller;

import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/admin/appointment")
public class AppointmentControllerAdmin {

    @Autowired
    private AppointmentService service;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAppointment(@RequestParam("size") Integer size,
                                               @RequestParam("page") Integer page)
    {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Appointments> allAppointment = service.getAllAppointment(pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(allAppointment);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAppointment(@RequestParam("id") Long appointmentId)
    {
        String s = service.deleteAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @GetMapping("/active")
    public ResponseEntity<?> fetchAllActive(@RequestParam("id") Long doctorId, @RequestParam("date") LocalDate date){
        List<Appointments> activeAppointment = service.getActiveAppointment(doctorId, date);
        return ResponseEntity.status(HttpStatus.OK).body(activeAppointment);
    }
}
