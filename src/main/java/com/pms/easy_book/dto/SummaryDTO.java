package com.pms.easy_book.dto;

import java.math.BigDecimal;

public class SummaryDTO {

    private int totalPatient;
    private int totalAppointment;
    private BigDecimal totalPayment;
    private int totalDoctor;

    public int getTotalPatient() {
        return totalPatient;
    }

    public void setTotalPatient(int totalPatient) {
        this.totalPatient = totalPatient;
    }

    public int getTotalAppointment() {
        return totalAppointment;
    }

    public void setTotalAppointment(int totalAppointment) {
        this.totalAppointment = totalAppointment;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public int getTotalDoctor() {
        return totalDoctor;
    }

    public void setTotalDoctor(int totalDoctor) {
        this.totalDoctor = totalDoctor;
    }
}
