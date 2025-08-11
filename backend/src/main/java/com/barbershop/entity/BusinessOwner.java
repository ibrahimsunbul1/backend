package com.barbershop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "business_owners")
public class BusinessOwner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "İşletme adı boş olamaz")
    @Column(name = "business_name", nullable = false)
    private String businessName;
    
    @NotBlank(message = "Sahip adı boş olamaz")
    @Column(name = "owner_name", nullable = false)
    private String ownerName;
    
    @NotBlank(message = "Sahip soyadı boş olamaz")
    @Column(name = "owner_surname", nullable = false)
    private String ownerSurname;
    
    @NotBlank(message = "Telefon alanı boş olamaz")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Geçerli bir telefon numarası girin")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    
    @NotBlank(message = "E-posta alanı boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi girin")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Adres alanı boş olamaz")
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;
    
    @NotBlank(message = "Şehir alanı boş olamaz")
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "district")
    private String district;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    @Column(name = "tax_number")
    private String taxNumber;
    
    @ElementCollection
    @CollectionTable(name = "business_services", joinColumns = @JoinColumn(name = "business_id"))
    @MapKeyColumn(name = "service_name")
    @Column(name = "service_price")
    private Map<String, Double> services;
    
    @ElementCollection
    @CollectionTable(name = "business_working_hours", joinColumns = @JoinColumn(name = "business_id"))
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "hours_info", columnDefinition = "TEXT")
    private Map<String, String> workingHours;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public BusinessOwner() {}
    
    public BusinessOwner(String businessName, String ownerName, String ownerSurname, String phone, String email) {
        this.businessName = businessName;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.phone = phone;
        this.email = email;
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
    
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    
    public String getOwnerFullName() {
        return ownerName + " " + ownerSurname;
    }
}