package com.barbershop.dto;

import com.barbershop.entity.Notification;
import java.time.LocalDateTime;

public class NotificationDTO {
    
    private Long id;
    
    private Long businessOwnerId;
    
    private Long appointmentId;
    
    private String title;
    
    private String message;
    
    private Notification.NotificationType type;
    
    private Boolean isRead;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime readAt;
    
    // Additional fields for response
    private String businessName;
    private String customerName;
    private LocalDateTime appointmentDate;
    
    // Constructors
    public NotificationDTO() {}
    
    public NotificationDTO(String title, String message, Notification.NotificationType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getBusinessOwnerId() {
        return businessOwnerId;
    }
    
    public void setBusinessOwnerId(Long businessOwnerId) {
        this.businessOwnerId = businessOwnerId;
    }
    
    public Long getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Notification.NotificationType getType() {
        return type;
    }
    
    public void setType(Notification.NotificationType type) {
        this.type = type;
    }
    
    public Boolean getIsRead() {
        return isRead;
    }
    
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getReadAt() {
        return readAt;
    }
    
    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
    
    public String getBusinessName() {
        return businessName;
    }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}