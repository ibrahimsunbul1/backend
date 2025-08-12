package com.barbershop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerDTO {
    
    private Long id;
    
    @NotBlank(message = "Ad alanı boş olamaz")
    private String firstName;
    
    @NotBlank(message = "Soyad alanı boş olamaz")
    private String lastName;
    
    @NotBlank(message = "Telefon alanı boş olamaz")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Geçerli bir telefon numarası girin")
    private String phone;
    
    @Email(message = "Geçerli bir e-posta adresi girin")
    private String email;
    
    @NotBlank(message = "Şifre alanı boş olamaz")
    private String password;
    
    private LocalDate birthDate;
    
    private List<String> preferredServices;
    
    private String notes;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Constructors
    public CustomerDTO() {}
    
    public CustomerDTO(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public List<String> getPreferredServices() {
        return preferredServices;
    }
    
    public void setPreferredServices(List<String> preferredServices) {
        this.preferredServices = preferredServices;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}