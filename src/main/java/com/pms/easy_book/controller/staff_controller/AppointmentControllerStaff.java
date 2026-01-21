package com.pms.easy_book.controller.staff_controller;

import com.pms.easy_book.dto.AppointmentSummaryDTO;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/staff")
public class AppointmentControllerStaff {

    @Autowired
    private AppointmentService service;

    @GetMapping("/ofDate/getAll")
    public ResponseEntity<?> getAllAppointment(@RequestParam("date") LocalDate date,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size)
    {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Appointments> appointmentOfDate = service.getAppointmentOfDate(date, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentOfDate);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAppointment(@RequestParam("name") String name,
                                               @RequestParam("code") String confirmationCode)
    {
        Appointments appointments = service.verifyAppointment(name, confirmationCode);
        return ResponseEntity.status(HttpStatus.OK).body(appointments);
    }

    @GetMapping("/today/appointment")
    public ResponseEntity<?> getAllAppointmentOfToday(@RequestParam Integer page,
                                                      @RequestParam Integer size)
    {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<AppointmentSummaryDTO> appointmentsOfTodayByPage = service.getAppointmentsOfTodayByPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsOfTodayByPage);
    }
}
