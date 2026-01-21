package com.pms.easy_book.service;

import com.pms.easy_book.dto.UserDTO;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.entity.Patient;
import com.pms.easy_book.entity.UserEntity;
import com.pms.easy_book.repo.AppointmentRepo;
import com.pms.easy_book.repo.PatientRepo;
import com.pms.easy_book.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean registerUser(UserDTO user){

        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + user.getUserName());
        }
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user.getUserName());
        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setAge(user.getAge());
        newUser.setGender(user.getGender());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(Arrays.asList("USER"));
        newUser.setPatient(null);
        newUser.setAppointments(null);

        userRepository.save(newUser);
        return true;
    }

    public boolean registerAdmin(UserDTO user){

        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + user.getUserName());
        }
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user.getUserName());
        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setAge(user.getAge());
        newUser.setGender(user.getGender());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(Arrays.asList("USER","STAFF","ADMIN"));
        newUser.setPatient(null);
        newUser.setAppointments(null);

        userRepository.save(newUser);
        return true;
    }

    public boolean registerStaff(UserDTO user){

        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + user.getUserName());
        }
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user.getUserName());
        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setAge(user.getAge());
        newUser.setGender(user.getGender());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(Arrays.asList("USER","STAFF"));
        newUser.setPatient(null);
        newUser.setAppointments(null);

        userRepository.save(newUser);
        return true;
    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    //Admin specific endpoint
    public String deleteUser(Long id){
        UserEntity existingUser = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("user not found"));

        List<Appointments> existingUserAppointment = existingUser.getAppointments();
        List<Patient> existingUserPatient = existingUser.getPatient();
        for(Appointments appointments : existingUserAppointment){
            appointments.setUser(null);
        }
        appointmentRepo.saveAll(existingUserAppointment);

        for(Patient patient : existingUserPatient){
            patient.setUser(null);
        }
        patientRepo.saveAll(existingUserPatient);

        return "User deleted successfully";
    }

    public String updatePassword(String existingEmail, String newPass){
        UserEntity userEntity = userRepository.findByUserName(existingEmail)
                .orElseThrow(() -> new RuntimeException("No user found"));

        userEntity.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(userEntity);
        return "Password updated successfully";
    }
}
