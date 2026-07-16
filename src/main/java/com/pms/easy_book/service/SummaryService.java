package com.pms.easy_book.service;

import com.pms.easy_book.Enum.BookingStatus;
import com.pms.easy_book.Enum.PaymentStatus;
import com.pms.easy_book.dto.SummaryDTO;
import com.pms.easy_book.exception.ResourceNotFound;
import com.pms.easy_book.repo.AppointmentRepo;
import com.pms.easy_book.repo.DoctorRepository;
import com.pms.easy_book.repo.PatientRepo;
import com.pms.easy_book.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SummaryService {

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private PatientRepo patientRepo;

    public SummaryDTO getLiveSummary(){
        SummaryDTO summary = new SummaryDTO();
        
        long appointmentCount = appointmentRepo.countByStatus(BookingStatus.SUCCESS);
        System.out.println("app count "+appointmentCount);
        long doctorCount = doctorRepository.count();
        System.out.println("doctor count "+doctorCount);
        long patientCount = patientRepo.count();
        System.out.println("patient count "+patientCount);
        BigDecimal totalPaymentAmountByStatus = paymentRepo.getTotalPaymentAmountByStatus(PaymentStatus.SUCCESSFUL);
        System.out.println("payment count "+totalPaymentAmountByStatus);
            summary.setTotalAppointment((int)appointmentCount);
            summary.setTotalDoctor((int)doctorCount);
            summary.setTotalPatient((int)patientCount);
            summary.setTotalPayment(totalPaymentAmountByStatus);

        return summary;
    }

}
