package com.barbershop.controller;

import com.barbershop.dto.AppointmentDTO;
import com.barbershop.entity.Appointment;
import com.barbershop.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        try {
            AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentDTO appointmentDTO) {
        try {
            AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(@PathVariable Long id, @RequestParam Appointment.AppointmentStatus status) {
        try {
            AppointmentDTO updatedAppointment = appointmentService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        try {
            AppointmentDTO appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAppointmentsByCustomer(@PathVariable Long customerId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByCustomer(customerId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/business/{businessOwnerId}")
    public ResponseEntity<?> getAppointmentsByBusinessOwner(@PathVariable Long businessOwnerId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByBusinessOwner(businessOwnerId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/business/{businessOwnerId}/status/{status}")
    public ResponseEntity<?> getAppointmentsByBusinessOwnerAndStatus(
            @PathVariable Long businessOwnerId, 
            @PathVariable Appointment.AppointmentStatus status) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByBusinessOwnerAndStatus(businessOwnerId, status);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByStatus(@PathVariable Appointment.AppointmentStatus status) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByStatus(status);
        return ResponseEntity.ok(appointments);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }
    
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(new SuccessResponse("Randevu başarıyla silindi"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/business/{businessOwnerId}/count")
    public ResponseEntity<?> getAppointmentCountByBusinessOwner(@PathVariable Long businessOwnerId) {
        try {
            long count = appointmentService.getAppointmentCountByBusinessOwner(businessOwnerId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
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