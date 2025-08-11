package com.barbershop.repository;

import com.barbershop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByPhone(String phone);
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %:name% OR c.lastName LIKE %:name%")
    List<Customer> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Customer c WHERE c.phone LIKE %:phone%")
    List<Customer> findByPhoneContaining(@Param("phone") String phone);
    
    @Query("SELECT c FROM Customer c ORDER BY c.createdAt DESC")
    List<Customer> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(c) FROM Customer c")
    long countAllCustomers();
}