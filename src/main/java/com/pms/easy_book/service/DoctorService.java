package com.pms.easy_book.service;

import com.pms.easy_book.dto.DoctorDto;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.entity.Doctors;
import com.pms.easy_book.repo.AppointmentRepo;
import com.pms.easy_book.repo.DoctorRepository;
import com.pms.easy_book.utils.PaginationUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {


    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepo repo;

    @Autowired
    private PaginationUtil util;


    public List<Doctors> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public Page<Doctors> getAllSpecializedDoctor(String specialization, Pageable pageable){
        List<Doctors> doctorsBySpecialization = getDoctorsBySpecialization(specialization);
        return util.paginate(doctorsBySpecialization,pageable);
    }
    

    public Doctors GetById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("doctor not found"));
    }


    @Transactional
    public Doctors aadNewDoc(DoctorDto doctor, MultipartFile imageFile) throws IOException {

        Doctors newDoctor = new Doctors();
        newDoctor.setName(doctor.getName());
        newDoctor.setDegree(doctor.getDegree());
        newDoctor.setExperience(doctor.getExperience());
        newDoctor.setFees(doctor.getFees());
        newDoctor.setSpecialization(doctor.getSpecialization());

        newDoctor.setImageName(imageFile.getOriginalFilename());
        newDoctor.setImageType(imageFile.getContentType());
        newDoctor.setImageData(imageFile.getBytes());

        newDoctor.setAppointment(null);

        return doctorRepository.save(newDoctor);

    }

    @Transactional
    public void deleteDoctor(Long id) {
        Optional<Doctors> existingDoctor = doctorRepository.findById(id);
        Doctors doctors = existingDoctor.get();
        if(!doctors.getAppointment().isEmpty()){
            throw new RuntimeException("Doctor can not ne deleted! Active appointments are there");
        }
        List<Appointments> appointment = doctors.getAppointment();
        for (Appointments appointment1 : appointment) {
            appointment1.setDoctor(null);
        }
        repo.saveAll(appointment);

    }
}
