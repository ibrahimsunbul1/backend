package com.barbershop.repository;

import com.barbershop.entity.BusinessOwner;
import com.barbershop.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByBusinessOwner(BusinessOwner businessOwner);
    
    @Query("SELECT n FROM Notification n WHERE n.businessOwner = :businessOwner ORDER BY n.createdAt DESC")
    List<Notification> findByBusinessOwnerOrderByCreatedAtDesc(@Param("businessOwner") BusinessOwner businessOwner);
    
    @Query("SELECT n FROM Notification n WHERE n.businessOwner = :businessOwner AND n.isRead = :isRead")
    List<Notification> findByBusinessOwnerAndIsRead(@Param("businessOwner") BusinessOwner businessOwner, @Param("isRead") Boolean isRead);
    
    @Query("SELECT n FROM Notification n WHERE n.businessOwner = :businessOwner AND n.isRead = false ORDER BY n.createdAt DESC")
    List<Notification> findUnreadByBusinessOwner(@Param("businessOwner") BusinessOwner businessOwner);
    
    @Query("SELECT n FROM Notification n WHERE n.type = :type")
    List<Notification> findByType(@Param("type") Notification.NotificationType type);
    
    @Query("SELECT n FROM Notification n WHERE n.businessOwner = :businessOwner AND n.type = :type")
    List<Notification> findByBusinessOwnerAndType(@Param("businessOwner") BusinessOwner businessOwner, @Param("type") Notification.NotificationType type);
    
    @Query("SELECT n FROM Notification n WHERE n.createdAt BETWEEN :startDate AND :endDate")
    List<Notification> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.businessOwner = :businessOwner AND n.isRead = false")
    long countUnreadByBusinessOwner(@Param("businessOwner") BusinessOwner businessOwner);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.businessOwner = :businessOwner")
    long countByBusinessOwner(@Param("businessOwner") BusinessOwner businessOwner);
    
    @Query("SELECT n FROM Notification n ORDER BY n.createdAt DESC")
    List<Notification> findAllOrderByCreatedAtDesc();
}