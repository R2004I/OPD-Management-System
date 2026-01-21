package com.pms.easy_book.service;

import com.pms.easy_book.Enum.Status;
import com.pms.easy_book.dto.AppointmentDto;
import com.pms.easy_book.dto.AppointmentSummaryDTO;
import com.pms.easy_book.entity.Appointments;
import com.pms.easy_book.entity.Doctors;
import com.pms.easy_book.entity.Patient;
import com.pms.easy_book.entity.UserEntity;
import com.pms.easy_book.exception.ResourceNotFound;
import com.pms.easy_book.repo.*;
import com.pms.easy_book.utils.PaginationUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaginationUtil pageUtil;


    public List<Appointments> getAll(){
        return appointmentRepo.findAll();
    }

    //Admin related & staff
    public Page<Appointments> getAllAppointment(Pageable pageable){
        List<Appointments> all = getAll();
        return pageUtil.paginate(all,pageable);
    }

    public List<Patient> getAllPatientList(){
        return patientRepo.findAll();
    }

    public Page<Patient> getAllPatients(Pageable pageable){
        List<Patient> allPatientList = getAllPatientList();
        return pageUtil.paginate(allPatientList,pageable);
    }


    public List<Appointments> getAppointment(LocalDate date){
        return appointmentRepo.findByAppointmentDate(date);
    }

    //Admin related service
    public Page<Appointments> getAppointmentOfDate(LocalDate date, Pageable pageable){
        List<Appointments> appointment = getAppointment(date);
        return pageUtil.paginate(appointment,pageable);
    }


    @Transactional
    public Appointments createAppointment(AppointmentDto appointment, Long doctorId, String username){
        Doctors doctors = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new ResourceNotFound("doctor not found"));
        UserEntity user = userRepo.findByUserName(username)
                .orElseThrow(()-> new ResourceNotFound("user not found"));

        String email =  appointment.getPatientEmail();
        String phone = appointment.getPatientPhoneNo();

        Patient existingPatient = patientRepo.findByEmailAndPhone(email, phone);

        System.out.println("Our patient data: "+existingPatient);
        Appointments newAppointment = new Appointments();

        if(existingPatient == null) {
            Patient newPatient = new Patient();
            newPatient.setName(appointment.getPatientName());
            newPatient.setEmail(appointment.getPatientEmail());
            newPatient.setPhone(appointment.getPatientPhoneNo());
            newPatient.setDepartment(appointment.getDepartment());
            newPatient.setDoctor(doctors.getName());
            //bidirectional mapping
            newPatient.setUser(user);
            user.getPatient().add(newPatient);
            userRepo.save(user);
            patientRepo.save(newPatient);
        }
        newAppointment.setDoctor(doctors);
        newAppointment.setPatientName(appointment.getPatientName());
        newAppointment.setPatientEmail(email);
        newAppointment.setAppointmentDate(appointment.getAppointmentDate());
        newAppointment.setPatientPhoneNo(phone);
        newAppointment.setDepartment(appointment.getDepartment());
        newAppointment.setUser(user);
        newAppointment.setAmount(50 + doctors.getFees());
        newAppointment.setStatus(Status.PENDING);
        newAppointment.setHasVisited(false);
        newAppointment.setPayment(null);

        appointmentRepo.save(newAppointment);
        //bi-directional mapping
        doctors.getAppointment().add(newAppointment);
        user.getAppointments().add(newAppointment);

        doctorRepository.save(doctors);
        userRepo.save(user);

        return newAppointment;
    }

    public int calculateTotalPrice(Long appointmentId){
        Appointments appointments = appointmentRepo.findById(appointmentId).orElseThrow(() -> new ResourceNotFound("not found"));
        return (appointments.getAmount());
    }


    public String cancelAppointment(Long appointmentId){
        Appointments existingAppointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFound("appointment not found"));
        if(existingAppointment.getStatus() == Status.CANCELLED){
            throw new RuntimeException("Appointment already cancelled");
        }
        if(existingAppointment.getAppointmentDate().isBefore(LocalDate.now())){
            throw  new RuntimeException("Appointment can't be cancelled");
        }
        existingAppointment.setStatus(Status.CANCELLED);

        Doctors doctor = existingAppointment.getDoctor();
        List<Appointments> appointmentOFDoctor = doctor.getAppointment();
        appointmentOFDoctor.remove(existingAppointment);

        return "appointment cancelled successfully";
    }


    public String deleteAppointment(Long appointmentId){
        Appointments appointments = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFound("appointment not found"));
        appointmentRepo.deleteById(appointmentId);
        return "appointment deleted successfully";
    }

    public Appointments verifyAppointment(String name, String code){
        System.out.println("name: "+name+"code: "+code);
        Appointments appointments = appointmentRepo
                .findByPatientNameAndCode(name,code);
        if(appointments == null){
            throw new RuntimeException("appointment not found");
        }
        else {
            appointments.setHasVisited(true);
            appointments.setStatus(Status.COMPLETED);
            appointmentRepo.save(appointments);
        }
        return appointments;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void updateStatus(){
        List<Appointments> byStatus = appointmentRepo.findByStatus(Status.PENDING);

        LocalDate today = LocalDate.now();
        for(Appointments appointment:byStatus){
            if(appointment.getAppointmentDate().isBefore(today)){
                appointment.setHasVisited(false);
                appointment.setStatus(Status.PENDING);
            }
        }

        appointmentRepo.saveAll(byStatus);
        System.out.println("Status updated successfully");
    }

    public List<Appointments> getActiveAppointment(Long doctorId, LocalDate date){
        return appointmentRepo.findAppointmentsByDoctorAndDate(doctorId, date);
    }


    public Appointments getAppointmentDetails(String code){
        return appointmentRepo.findByConfirmationCode(code);
    }

    public Page<AppointmentSummaryDTO> getAppointmentsOfTodayByPage(Pageable pageable){
        List<AppointmentSummaryDTO> byAppointmentDate = appointmentRepo.findTodayAppointments();
       return pageUtil.paginate(byAppointmentDate,pageable);
    }
}
