package com.pms.easy_book.dto;

public class AppointmentSummaryDTO {

    private String patientName;
    private String patientPhoneNo;
    private String patientEmail;
    private String department;

    // Constructor
    public AppointmentSummaryDTO(String patientName, String patientPhoneNo, String patientEmail, String department) {
        this.patientName = patientName;
        this.patientPhoneNo = patientPhoneNo;
        this.patientEmail = patientEmail;
        this.department = department;
    }

    // Getters
    public String getPatientName() {
        return patientName;
    }

    public String getPatientPhoneNo() {
        return patientPhoneNo;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public String getDepartment() {
        return department;
    }
}
