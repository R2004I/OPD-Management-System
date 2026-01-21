package com.pms.easy_book.controller.admin_controller;


import com.pms.easy_book.dto.UserDTO;
import com.pms.easy_book.entity.Patient;
import com.pms.easy_book.service.AppointmentService;
import com.pms.easy_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/admin/all/user")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @DeleteMapping("/admin/delete/user")
    public ResponseEntity<?> deleteUser(@RequestParam("id") Long id){
        String s = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @PostMapping("/signup-staff")
    public ResponseEntity<?> registerStaff(@RequestBody UserDTO user){
        boolean b = userService.registerStaff(user);
        return new ResponseEntity<>(b, HttpStatus.CREATED);
    }

    @GetMapping("/all/patient")
    public ResponseEntity<?> getAllPatient(@RequestParam("size") Integer size,
                                           @RequestParam("page") Integer page)
    {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Patient> allPatients = appointmentService.getAllPatients(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allPatients);
    }

}
