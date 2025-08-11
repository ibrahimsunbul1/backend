package com.barbershop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_owner_id", nullable = false)
    @NotNull(message = "İşletme sahibi bilgisi gereklidir")
    private BusinessOwner businessOwner;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @NotBlank(message = "Bildirim başlığı boş olamaz")
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotBlank(message = "Bildirim mesajı boş olamaz")
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;
    
    @Column(name = "is_read")
    private Boolean isRead = false;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "read_at")
    private LocalDateTime readAt;
    
    public enum NotificationType {
        NEW_APPOINTMENT("Yeni Randevu"),
        APPOINTMENT_CANCELLED("Randevu İptali"),
        APPOINTMENT_UPDATED("Randevu Güncelleme"),
        SYSTEM("Sistem Bildirimi");
        
        private final String displayName;
        
        NotificationType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Notification() {}
    
    public Notification(BusinessOwner businessOwner, String title, String message, NotificationType type) {
        this.businessOwner = businessOwner;
        this.title = title;
        this.message = message;
        this.type = type;
    }
    
    public Notification(BusinessOwner businessOwner, Appointment appointment, String title, String message, NotificationType type) {
        this.businessOwner = businessOwner;
        this.appointment = appointment;
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
    
    public BusinessOwner getBusinessOwner() {
        return businessOwner;
    }
    
    public void setBusinessOwner(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
    }
    
    public Appointment getAppointment() {
        return appointment;
    }
    
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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
    
    public NotificationType getType() {
        return type;
    }
    
    public void setType(NotificationType type) {
        this.type = type;
    }
    
    public Boolean getIsRead() {
        return isRead;
    }
    
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
        if (isRead && readAt == null) {
            this.readAt = LocalDateTime.now();
        }
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
    
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }
}