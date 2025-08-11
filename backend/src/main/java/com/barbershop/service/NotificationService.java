package com.barbershop.service;

import com.barbershop.dto.NotificationDTO;
import com.barbershop.entity.Appointment;
import com.barbershop.entity.BusinessOwner;
import com.barbershop.entity.Notification;
import com.barbershop.repository.BusinessOwnerRepository;
import com.barbershop.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private BusinessOwnerRepository businessOwnerRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    
    @Async
    public void sendNewAppointmentNotification(Appointment appointment) {
        String title = "Yeni Randevu Talebi";
        String message = String.format(
            "%s adlı müşteri %s tarihinde randevu talebinde bulundu. Hizmetler: %s",
            appointment.getCustomer().getFullName(),
            appointment.getAppointmentDate().format(dateFormatter),
            String.join(", ", appointment.getServices() != null ? appointment.getServices() : List.of("Belirtilmemiş"))
        );
        
        Notification notification = new Notification(
            appointment.getBusinessOwner(),
            appointment,
            title,
            message,
            Notification.NotificationType.NEW_APPOINTMENT
        );
        
        Notification savedNotification = notificationRepository.save(notification);
        
        // WebSocket ile gerçek zamanlı bildirim gönder
        sendRealTimeNotification(savedNotification);
        
        // Randevunun bildirim gönderildi olarak işaretle
        appointment.setNotificationSent(true);
    }
    
    @Async
    public void sendAppointmentUpdateNotification(Appointment appointment) {
        String title = "Randevu Güncellendi";
        String message = String.format(
            "%s adlı müşterinin %s tarihli randevusu güncellendi.",
            appointment.getCustomer().getFullName(),
            appointment.getAppointmentDate().format(dateFormatter)
        );
        
        Notification notification = new Notification(
            appointment.getBusinessOwner(),
            appointment,
            title,
            message,
            Notification.NotificationType.APPOINTMENT_UPDATED
        );
        
        Notification savedNotification = notificationRepository.save(notification);
        sendRealTimeNotification(savedNotification);
    }
    
    @Async
    public void sendAppointmentCancelledNotification(Appointment appointment) {
        String title = "Randevu İptal Edildi";
        String message = String.format(
            "%s adlı müşterinin %s tarihli randevusu iptal edildi.",
            appointment.getCustomer().getFullName(),
            appointment.getAppointmentDate().format(dateFormatter)
        );
        
        Notification notification = new Notification(
            appointment.getBusinessOwner(),
            appointment,
            title,
            message,
            Notification.NotificationType.APPOINTMENT_CANCELLED
        );
        
        Notification savedNotification = notificationRepository.save(notification);
        sendRealTimeNotification(savedNotification);
    }
    
    public NotificationDTO createSystemNotification(Long businessOwnerId, String title, String message) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        Notification notification = new Notification(
            businessOwner,
            title,
            message,
            Notification.NotificationType.SYSTEM
        );
        
        Notification savedNotification = notificationRepository.save(notification);
        sendRealTimeNotification(savedNotification);
        
        return convertToDTO(savedNotification);
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByBusinessOwner(Long businessOwnerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        return notificationRepository.findByBusinessOwnerOrderByCreatedAtDesc(businessOwner)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotificationsByBusinessOwner(Long businessOwnerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        return notificationRepository.findUnreadByBusinessOwner(businessOwner)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public long getUnreadNotificationCount(Long businessOwnerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        return notificationRepository.countUnreadByBusinessOwner(businessOwner);
    }
    
    public NotificationDTO markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Bildirim bulunamadı"));
        
        notification.markAsRead();
        Notification updatedNotification = notificationRepository.save(notification);
        
        return convertToDTO(updatedNotification);
    }
    
    public void markAllNotificationsAsRead(Long businessOwnerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        List<Notification> unreadNotifications = notificationRepository.findUnreadByBusinessOwner(businessOwner);
        
        for (Notification notification : unreadNotifications) {
            notification.markAsRead();
        }
        
        notificationRepository.saveAll(unreadNotifications);
    }
    
    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Bildirim bulunamadı");
        }
        notificationRepository.deleteById(notificationId);
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByType(Notification.NotificationType type) {
        return notificationRepository.findByType(type)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return notificationRepository.findByCreatedAtBetween(startDate, endDate)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private void sendRealTimeNotification(Notification notification) {
        try {
            NotificationDTO notificationDTO = convertToDTO(notification);
            String destination = "/topic/notifications/" + notification.getBusinessOwner().getId();
            messagingTemplate.convertAndSend(destination, notificationDTO);
        } catch (Exception e) {
            // Log the error but don't fail the notification creation
            System.err.println("WebSocket bildirim gönderilirken hata oluştu: " + e.getMessage());
        }
    }
    
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setBusinessOwnerId(notification.getBusinessOwner().getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setIsRead(notification.getIsRead());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setReadAt(notification.getReadAt());
        
        // Business name
        dto.setBusinessName(notification.getBusinessOwner().getBusinessName());
        
        // Appointment bilgileri (eğer varsa)
        if (notification.getAppointment() != null) {
            dto.setAppointmentId(notification.getAppointment().getId());
            dto.setCustomerName(notification.getAppointment().getCustomer().getFullName());
            dto.setAppointmentDate(notification.getAppointment().getAppointmentDate());
        }
        
        return dto;
    }
}