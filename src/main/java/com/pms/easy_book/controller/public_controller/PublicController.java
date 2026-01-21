package com.pms.easy_book.controller.public_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.easy_book.dto.LoginDTO;
import com.pms.easy_book.dto.UserDTO;
import com.pms.easy_book.entity.Doctors;
import com.pms.easy_book.service.DoctorService;
import com.pms.easy_book.service.UserDetailsServiceImpl;
import com.pms.easy_book.service.UserService;
import com.pms.easy_book.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl serviceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup-user")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user){
        boolean b = userService.registerUser(user);
        return new ResponseEntity<>(b, HttpStatus.CREATED);
    }

    @PostMapping("/signup-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO user){
        boolean b = userService.registerAdmin(user);
        return new ResponseEntity<>(b, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO user){
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            UserDetails userDetails = serviceImpl.loadUserByUsername(user.getUserName());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> getDoctorsBySpecialization(@RequestParam String specialization,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("size") Integer size)
    {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Doctors> allSpecializedDoctor = doctorService.getAllSpecializedDoctor(specialization, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allSpecializedDoctor);
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam("id") Long doctorId){
        Doctors doctors = doctorService.GetById(doctorId);
        byte[] imageData = doctors.getImageData();
        return ResponseEntity.status(HttpStatus.OK).body(imageData);
    }

    @GetMapping("/details")
    public Doctors GetById(@RequestParam("doctorId") Long id){
        return  doctorService.GetById(id);
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestParam("email") String existEmail,
                                            @RequestParam("password") String password)
    {
        String s = userService.updatePassword(existEmail, password);
        return ResponseEntity.status(HttpStatus.OK).body(s);

    }

}
