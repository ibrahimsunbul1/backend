package com.barbershop.service;

import com.barbershop.dto.AppointmentDTO;
import com.barbershop.entity.Appointment;
import com.barbershop.entity.BusinessOwner;
import com.barbershop.entity.Customer;
import com.barbershop.repository.AppointmentRepository;
import com.barbershop.repository.BusinessOwnerRepository;
import com.barbershop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BusinessOwnerRepository businessOwnerRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        // Müşteri kontrolü
        Customer customer = customerRepository.findById(appointmentDTO.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
        
        // İşletme sahibi kontrolü
        BusinessOwner businessOwner = businessOwnerRepository.findById(appointmentDTO.getBusinessOwnerId())
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        // Randevu tarihi kontrolü (geçmiş tarih olamaz)
        if (appointmentDTO.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Randevu tarihi geçmiş bir tarih olamaz");
        }
        
        Appointment appointment = convertToEntity(appointmentDTO, customer, businessOwner);
        
        // Toplam fiyat hesaplama
        if (appointmentDTO.getServices() != null && !appointmentDTO.getServices().isEmpty()) {
            double totalPrice = calculateTotalPrice(appointmentDTO.getServices(), businessOwner);
            appointment.setTotalPrice(totalPrice);
        }
        
        Appointment savedAppointment = appointmentRepository.save(appointment);
        
        // Bildirim gönder
        notificationService.sendNewAppointmentNotification(savedAppointment);
        
        return convertToDTO(savedAppointment);
    }
    
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Appointment existingAppointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        
        // Randevu tarihi kontrolü (geçmiş tarih olamaz)
        if (appointmentDTO.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Randevu tarihi geçmiş bir tarih olamaz");
        }
        
        updateAppointmentFields(existingAppointment, appointmentDTO);
        
        // Toplam fiyat yeniden hesaplama
        if (appointmentDTO.getServices() != null && !appointmentDTO.getServices().isEmpty()) {
            double totalPrice = calculateTotalPrice(appointmentDTO.getServices(), existingAppointment.getBusinessOwner());
            existingAppointment.setTotalPrice(totalPrice);
        }
        
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        
        // Güncelleme bildirimi gönder
        notificationService.sendAppointmentUpdateNotification(updatedAppointment);
        
        return convertToDTO(updatedAppointment);
    }
    
    public AppointmentDTO updateAppointmentStatus(Long id, Appointment.AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        
        appointment.setStatus(status);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        
        return convertToDTO(updatedAppointment);
    }
    
    @Transactional(readOnly = true)
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        return convertToDTO(appointment);
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
        
        return appointmentRepository.findByCustomerOrderByAppointmentDateDesc(customer)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByBusinessOwner(Long businessOwnerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        return appointmentRepository.findByBusinessOwnerOrderByAppointmentDateDesc(businessOwner)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointmentRepository.findByStatus(status)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByBusinessOwnerAndStatus(Long businessOwnerId, Appointment.AppointmentStatus status) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        return appointmentRepository.findByBusinessOwnerAndStatus(businessOwner, status)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByAppointmentDateBetween(startDate, endDate)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAllOrderByCreatedAtDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        
        // İptal bildirimi gönder
        notificationService.sendAppointmentCancelledNotification(appointment);
        
        appointmentRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long getAppointmentCountByBusinessOwner(Long businessOwnerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
            .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        return appointmentRepository.countByBusinessOwner(businessOwner);
    }
    
    private double calculateTotalPrice(List<String> services, BusinessOwner businessOwner) {
        double total = 0.0;
        if (businessOwner.getServices() != null) {
            for (String service : services) {
                Double price = businessOwner.getServices().get(service);
                if (price != null) {
                    total += price;
                }
            }
        }
        return total;
    }
    
    private Appointment convertToEntity(AppointmentDTO dto, Customer customer, BusinessOwner businessOwner) {
        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setBusinessOwner(businessOwner);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        // Convert List<String> to comma-separated String
        if (dto.getServices() != null && !dto.getServices().isEmpty()) {
            appointment.setServices(String.join(", ", dto.getServices()));
        }
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : Appointment.AppointmentStatus.PENDING);
        return appointment;
    }
    
    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setCustomerId(appointment.getCustomer().getId());
        dto.setBusinessOwnerId(appointment.getBusinessOwner().getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        // Convert comma-separated String to List<String>
        if (appointment.getServices() != null && !appointment.getServices().trim().isEmpty()) {
            dto.setServices(List.of(appointment.getServices().split(",\\s*")));
        } else {
            dto.setServices(List.of());
        }
        dto.setTotalPrice(appointment.getTotalPrice());
        dto.setNotes(appointment.getNotes());
        dto.setStatus(appointment.getStatus());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());
        dto.setNotificationSent(appointment.getNotificationSent());
        
        // Customer bilgileri
        dto.setCustomerName(appointment.getCustomer().getFullName());
        dto.setCustomerPhone(appointment.getCustomer().getPhone());
        dto.setCustomerEmail(appointment.getCustomer().getEmail());
        
        // Business Owner bilgileri
        dto.setBusinessName(appointment.getBusinessOwner().getBusinessName());
        dto.setBusinessOwnerName(appointment.getBusinessOwner().getOwnerFullName());
        dto.setBusinessOwnerPhone(appointment.getBusinessOwner().getPhone());
        
        return dto;
    }
    
    private void updateAppointmentFields(Appointment appointment, AppointmentDTO dto) {
        appointment.setAppointmentDate(dto.getAppointmentDate());
        // Convert List<String> to comma-separated String
        if (dto.getServices() != null && !dto.getServices().isEmpty()) {
            appointment.setServices(String.join(", ", dto.getServices()));
        }
        appointment.setNotes(dto.getNotes());
        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }
    }
}