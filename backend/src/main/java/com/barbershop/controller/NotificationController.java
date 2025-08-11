package com.barbershop.controller;

import com.barbershop.dto.NotificationDTO;
import com.barbershop.entity.Notification;
import com.barbershop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/system")
    public ResponseEntity<?> createSystemNotification(
            @RequestParam Long businessOwnerId,
            @RequestParam String title,
            @RequestParam String message) {
        try {
            NotificationDTO notification = notificationService.createSystemNotification(businessOwnerId, title, message);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/business/{businessOwnerId}")
    public ResponseEntity<?> getNotificationsByBusinessOwner(@PathVariable Long businessOwnerId) {
        try {
            List<NotificationDTO> notifications = notificationService.getNotificationsByBusinessOwner(businessOwnerId);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/business/{businessOwnerId}/unread")
    public ResponseEntity<?> getUnreadNotificationsByBusinessOwner(@PathVariable Long businessOwnerId) {
        try {
            List<NotificationDTO> notifications = notificationService.getUnreadNotificationsByBusinessOwner(businessOwnerId);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/business/{businessOwnerId}/unread/count")
    public ResponseEntity<?> getUnreadNotificationCount(@PathVariable Long businessOwnerId) {
        try {
            long count = notificationService.getUnreadNotificationCount(businessOwnerId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId) {
        try {
            NotificationDTO notification = notificationService.markNotificationAsRead(notificationId);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @PatchMapping("/business/{businessOwnerId}/read-all")
    public ResponseEntity<?> markAllNotificationsAsRead(@PathVariable Long businessOwnerId) {
        try {
            notificationService.markAllNotificationsAsRead(businessOwnerId);
            return ResponseEntity.ok(new SuccessResponse("Tüm bildirimler okundu olarak işaretlendi"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        try {
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok(new SuccessResponse("Bildirim başarıyla silindi"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByType(@PathVariable Notification.NotificationType type) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(notifications);
    }
    
    // Error Response Class
    public static class ErrorResponse {
        private String message;
        private long timestamp;
        
        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
    
    // Success Response Class
    public static class SuccessResponse {
        private String message;
        private long timestamp;
        
        public SuccessResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}