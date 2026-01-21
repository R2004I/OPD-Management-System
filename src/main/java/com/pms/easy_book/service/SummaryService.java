package com.pms.easy_book.service;

import com.pms.easy_book.dto.SummaryDTO;
import com.pms.easy_book.exception.ResourceNotFound;
import com.pms.easy_book.repo.AppointmentRepo;
import com.pms.easy_book.repo.DoctorRepository;
import com.pms.easy_book.repo.PatientRepo;
import com.pms.easy_book.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        SummaryDTO ns = new SummaryDTO();

        if((int) patientRepo.count()>0
                && (int) appointmentRepo.count()>0
                && (int) doctorRepository.count()>0)
        {
            ns.setTotalPatient((int) patientRepo.count());
            ns.setTotalDoctor((int) doctorRepository.count());
            ns.setTotalAppointment((int) appointmentRepo.count());
        }
        else{
            ns.setTotalAppointment(0);
            ns.setTotalDoctor(0);
            ns.setTotalPatient(0);
        }

        paymentRepo.getTotalPaymentAmount().orElseThrow(()-> new ResourceNotFound("No payment done yet"));
        if(paymentRepo.getTotalPaymentAmount().get()>0){
            ns.setTotalPayment(paymentRepo.getTotalPaymentAmount().get());
        }else {
            ns.setTotalPayment(0);
        }
        return ns;
    }

}
