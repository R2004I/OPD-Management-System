package com.pms.easy_book.dto;

import com.pms.easy_book.Enum.BookingStatus;

public class QRVerificationResponse {

    private String patientName;
    private String patientEmail;
    private String patientPhone;
    private BookingStatus status;
    private String department;
    private String doctorName;

    public QRVerificationResponse() {
    }

    public QRVerificationResponse(String patientName, String patientEmail, String patientPhone, BookingStatus status, String department, String doctorName) {
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.patientPhone = patientPhone;
        this.status = status;
        this.department = department;
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
