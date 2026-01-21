package com.pms.easy_book.controller.admin_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.easy_book.dto.DoctorDto;
import com.pms.easy_book.entity.Doctors;
import com.pms.easy_book.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/register-new-doctor")
    public ResponseEntity<?> aadNewDoctor(@RequestParam("doctorData") String doctor,
                                          @RequestParam("image") MultipartFile image) throws IOException
    {
        DoctorDto dto = mapper.readValue(doctor,DoctorDto.class);
        Doctors doctors = doctorService.aadNewDoc(dto,image);
        return ResponseEntity.status(HttpStatus.OK).body(doctors);
    }

    @DeleteMapping("/delete")
    public void deleteDoc(@RequestParam("id") Long id)
    {
        doctorService.deleteDoctor(id);
    }
}
