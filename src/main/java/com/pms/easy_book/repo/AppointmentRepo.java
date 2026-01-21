package com.pms.easy_book.repo;

import com.pms.easy_book.Enum.Status;
import com.pms.easy_book.dto.AppointmentSummaryDTO;
import com.pms.easy_book.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointments,Long> {

    public List<Appointments> findByStatus(Status status);

    @Query("SELECT a FROM Appointments a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :date")
    List<Appointments> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date);

    @Query("SELECT a FROM Appointments a WHERE a.patientName = :name AND a.confirmationCode = :code")
    Appointments findByPatientNameAndCode(String name, String code);

    List<Appointments> findByAppointmentDate(LocalDate date);

    Appointments findByConfirmationCode(String confirmationCode);

    @Query("SELECT new com.pms.easy_book.dto.AppointmentSummaryDTO(a.patientName, a.patientPhoneNo, a.patientEmail, a.department) " +
            "FROM Appointments a WHERE a.appointmentDate = CURRENT_DATE")
    List<AppointmentSummaryDTO> findTodayAppointments();

}
