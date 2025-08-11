package com.barbershop.dto;

import com.barbershop.entity.Appointment;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDTO {
    
    private Long id;
    
    @NotNull(message = "Müşteri ID gereklidir")
    private Long customerId;
    
    @NotNull(message = "İşletme sahibi ID gereklidir")
    private Long businessOwnerId;
    
    @NotNull(message = "Randevu tarihi gereklidir")
    private LocalDateTime appointmentDate;
    
    private List<String> services;
    
    private Double totalPrice;
    
    private String notes;
    
    private Appointment.AppointmentStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean notificationSent;
    
    // Customer bilgileri (response için)
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    
    // Business Owner bilgileri (response için)
    private String businessName;
    private String businessOwnerName;
    private String businessOwnerPhone;
    
    // Constructors
    public AppointmentDTO() {}
    
    public AppointmentDTO(Long customerId, Long businessOwnerId, LocalDateTime appointmentDate) {
        this.customerId = customerId;
        this.businessOwnerId = businessOwnerId;
        this.appointmentDate = appointmentDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public Long getBusinessOwnerId() {
        return businessOwnerId;
    }
    
    public void setBusinessOwnerId(Long businessOwnerId) {
        this.businessOwnerId = businessOwnerId;
    }
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public List<String> getServices() {
        return services;
    }
    
    public void setServices(List<String> services) {
        this.services = services;
    }
    
    public Double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Appointment.AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(Appointment.AppointmentStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getNotificationSent() {
        return notificationSent;
    }
    
    public void setNotificationSent(Boolean notificationSent) {
        this.notificationSent = notificationSent;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getBusinessName() {
        return businessName;
    }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getBusinessOwnerName() {
        return businessOwnerName;
    }
    
    public void setBusinessOwnerName(String businessOwnerName) {
        this.businessOwnerName = businessOwnerName;
    }
    
    public String getBusinessOwnerPhone() {
        return businessOwnerPhone;
    }
    
    public void setBusinessOwnerPhone(String businessOwnerPhone) {
        this.businessOwnerPhone = businessOwnerPhone;
    }
}