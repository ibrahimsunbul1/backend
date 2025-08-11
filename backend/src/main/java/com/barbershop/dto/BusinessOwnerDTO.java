package com.barbershop.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

public class BusinessOwnerDTO {
    
    private Long id;
    
    @NotBlank(message = "İşletme adı boş olamaz")
    @Size(min = 2, max = 100, message = "İşletme adı 2-100 karakter arasında olmalıdır")
    private String businessName;
    
    @NotBlank(message = "Sahip adı boş olamaz")
    @Size(min = 2, max = 50, message = "Sahip adı 2-50 karakter arasında olmalıdır")
    private String ownerName;
    
    @NotBlank(message = "Sahip soyadı boş olamaz")
    @Size(min = 2, max = 50, message = "Sahip soyadı 2-50 karakter arasında olmalıdır")
    private String ownerSurname;
    
    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefon numarası 10-11 haneli olmalıdır")
    private String phone;
    
    @NotBlank(message = "E-posta adresi boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    private String email;
    
    @NotBlank(message = "Adres boş olamaz")
    @Size(max = 500, message = "Adres 500 karakterden fazla olamaz")
    private String address;
    
    @NotBlank(message = "Şehir boş olamaz")
    @Size(min = 2, max = 50, message = "Şehir 2-50 karakter arasında olmalıdır")
    private String city;
    
    @NotBlank(message = "İlçe boş olamaz")
    @Size(min = 2, max = 50, message = "İlçe 2-50 karakter arasında olmalıdır")
    private String district;
    
    @Pattern(regexp = "^[0-9]{5}$", message = "Posta kodu 5 haneli olmalıdır")
    private String postalCode;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Vergi numarası 10-11 haneli olmalıdır")
    private String taxNumber;
    
    private Map<String, Double> services;

    private Map<String, String> workingHours;
    
    @Size(max = 1000, message = "Açıklama 1000 karakterden fazla olamaz")
    private String description;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public BusinessOwnerDTO() {}
    
    public BusinessOwnerDTO(String businessName, String ownerName, String ownerSurname, 
                           String phone, String email, String address, String city, 
                           String district, String postalCode, String taxNumber, 
                           Map<String, Double> services, Map<String, String> workingHours, String description) {
        this.businessName = businessName;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.district = district;
        this.postalCode = postalCode;
        this.taxNumber = taxNumber;
        this.services = services;
        this.workingHours = workingHours;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBusinessName() {
        return businessName;
    }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getOwnerSurname() {
        return ownerSurname;
    }
    
    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getTaxNumber() {
        return taxNumber;
    }
    
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    
    public Map<String, Double> getServices() {
        return services;
    }

    public void setServices(Map<String, Double> services) {
        this.services = services;
    }

    public Map<String, String> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Map<String, String> workingHours) {
        this.workingHours = workingHours;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    @Override
    public String toString() {
        return "BusinessOwnerDTO{" +
                "id=" + id +
                ", businessName='" + businessName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerSurname='" + ownerSurname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}