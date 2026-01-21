package com.pms.easy_book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pms.easy_book.Enum.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    private String orderId;
    private String paymentId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    @JsonIgnore
    private Appointments appointments;

    //getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Appointments getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointments appointments) {
        this.appointments = appointments;
    }
}
