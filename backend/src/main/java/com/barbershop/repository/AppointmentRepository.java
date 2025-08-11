package com.barbershop.repository;

import com.barbershop.entity.Appointment;
import com.barbershop.entity.BusinessOwner;
import com.barbershop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByCustomer(Customer customer);
    
    List<Appointment> findByBusinessOwner(BusinessOwner businessOwner);
    
    @Query("SELECT a FROM Appointment a WHERE a.businessOwner = :businessOwner ORDER BY a.appointmentDate DESC")
    List<Appointment> findByBusinessOwnerOrderByAppointmentDateDesc(@Param("businessOwner") BusinessOwner businessOwner);
    
    @Query("SELECT a FROM Appointment a WHERE a.customer = :customer ORDER BY a.appointmentDate DESC")
    List<Appointment> findByCustomerOrderByAppointmentDateDesc(@Param("customer") Customer customer);
    
    @Query("SELECT a FROM Appointment a WHERE a.status = :status")
    List<Appointment> findByStatus(@Param("status") Appointment.AppointmentStatus status);
    
    @Query("SELECT a FROM Appointment a WHERE a.businessOwner = :businessOwner AND a.status = :status")
    List<Appointment> findByBusinessOwnerAndStatus(@Param("businessOwner") BusinessOwner businessOwner, @Param("status") Appointment.AppointmentStatus status);
    
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByAppointmentDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Appointment a WHERE a.businessOwner = :businessOwner AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByBusinessOwnerAndAppointmentDateBetween(@Param("businessOwner") BusinessOwner businessOwner, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Appointment a WHERE a.notificationSent = false")
    List<Appointment> findByNotificationSentFalse();
    
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.businessOwner = :businessOwner")
    long countByBusinessOwner(@Param("businessOwner") BusinessOwner businessOwner);
    
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.businessOwner = :businessOwner AND a.status = :status")
    long countByBusinessOwnerAndStatus(@Param("businessOwner") BusinessOwner businessOwner, @Param("status") Appointment.AppointmentStatus status);
    
    @Query("SELECT a FROM Appointment a ORDER BY a.createdAt DESC")
    List<Appointment> findAllOrderByCreatedAtDesc();
}