package com.barbershop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Müşteri bilgisi gereklidir")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_owner_id", nullable = false)
    @NotNull(message = "İşletme sahibi bilgisi gereklidir")
    private BusinessOwner businessOwner;
    
    @Column(name = "appointment_date", nullable = false)
    @NotNull(message = "Randevu tarihi gereklidir")
    private LocalDateTime appointmentDate;
    
    @Column(name = "services", columnDefinition = "TEXT")
    private String services;
    
    @Column(name = "total_price")
    private Double totalPrice;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "notification_sent")
    private Boolean notificationSent = false;
    
    public enum AppointmentStatus {
        PENDING("Beklemede"),
        CONFIRMED("Onaylandı"),
        COMPLETED("Tamamlandı"),
        CANCELLED("İptal Edildi");
        
        private final String displayName;
        
        AppointmentStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Appointment() {}
    
    public Appointment(Customer customer, BusinessOwner businessOwner, LocalDateTime appointmentDate) {
        this.customer = customer;
        this.businessOwner = businessOwner;
        this.appointmentDate = appointmentDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public BusinessOwner getBusinessOwner() {
        return businessOwner;
    }
    
    public void setBusinessOwner(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
    }
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public String getServices() {
        return services;
    }
    
    public void setServices(String services) {
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
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
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
}