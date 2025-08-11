package com.barbershop.repository;

import com.barbershop.entity.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, Long> {
    
    Optional<BusinessOwner> findByPhone(String phone);
    
    Optional<BusinessOwner> findByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT b FROM BusinessOwner b WHERE b.businessName LIKE %:name%")
    List<BusinessOwner> findByBusinessNameContaining(@Param("name") String name);
    
    @Query("SELECT b FROM BusinessOwner b WHERE LOWER(b.businessName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY b.createdAt DESC")
    List<BusinessOwner> findByBusinessNameContainingIgnoreCaseOrderByCreatedAtDesc(@Param("name") String name);
    
    @Query("SELECT b FROM BusinessOwner b WHERE b.city = :city")
    List<BusinessOwner> findByCity(@Param("city") String city);
    
    @Query("SELECT b FROM BusinessOwner b WHERE LOWER(b.city) = LOWER(:city) ORDER BY b.createdAt DESC")
    List<BusinessOwner> findByCityIgnoreCaseOrderByCreatedAtDesc(@Param("city") String city);
    
    @Query("SELECT b FROM BusinessOwner b WHERE b.city = :city AND b.district = :district")
    List<BusinessOwner> findByCityAndDistrict(@Param("city") String city, @Param("district") String district);
    
    @Query("SELECT b FROM BusinessOwner b WHERE LOWER(b.city) = LOWER(:city) AND LOWER(b.district) = LOWER(:district) ORDER BY b.createdAt DESC")
    List<BusinessOwner> findByCityIgnoreCaseAndDistrictIgnoreCaseOrderByCreatedAtDesc(@Param("city") String city, @Param("district") String district);
    
    @Query("SELECT b FROM BusinessOwner b ORDER BY b.createdAt DESC")
    List<BusinessOwner> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(b) FROM BusinessOwner b")
    long countAllBusinessOwners();
}