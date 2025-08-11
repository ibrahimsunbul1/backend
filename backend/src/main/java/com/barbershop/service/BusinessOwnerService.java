package com.barbershop.service;

import com.barbershop.dto.BusinessOwnerDTO;
import com.barbershop.entity.BusinessOwner;
import com.barbershop.repository.BusinessOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BusinessOwnerService {
    
    @Autowired
    private BusinessOwnerRepository businessOwnerRepository;
    
    public BusinessOwnerDTO createBusinessOwner(BusinessOwnerDTO businessOwnerDTO) {
        // Check if phone already exists
        if (businessOwnerRepository.existsByPhone(businessOwnerDTO.getPhone())) {
            throw new RuntimeException("Bu telefon numarası ile kayıtlı bir işletme sahibi zaten mevcut");
        }
        
        // Check if email already exists
        if (businessOwnerRepository.existsByEmail(businessOwnerDTO.getEmail())) {
            throw new RuntimeException("Bu e-posta adresi ile kayıtlı bir işletme sahibi zaten mevcut");
        }
        
        BusinessOwner businessOwner = convertToEntity(businessOwnerDTO);
        BusinessOwner savedBusinessOwner = businessOwnerRepository.save(businessOwner);
        return convertToDTO(savedBusinessOwner);
    }
    
    public BusinessOwnerDTO updateBusinessOwner(Long id, BusinessOwnerDTO businessOwnerDTO) {
        BusinessOwner existingBusinessOwner = businessOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        
        // Check if phone is being changed and if new phone already exists
        if (!existingBusinessOwner.getPhone().equals(businessOwnerDTO.getPhone()) && 
            businessOwnerRepository.existsByPhone(businessOwnerDTO.getPhone())) {
            throw new RuntimeException("Bu telefon numarası ile kayıtlı başka bir işletme sahibi mevcut");
        }
        
        // Check if email is being changed and if new email already exists
        if (!existingBusinessOwner.getEmail().equals(businessOwnerDTO.getEmail()) && 
            businessOwnerRepository.existsByEmail(businessOwnerDTO.getEmail())) {
            throw new RuntimeException("Bu e-posta adresi ile kayıtlı başka bir işletme sahibi mevcut");
        }
        
        // Update fields
        existingBusinessOwner.setBusinessName(businessOwnerDTO.getBusinessName());
        existingBusinessOwner.setOwnerName(businessOwnerDTO.getOwnerName());
        existingBusinessOwner.setOwnerSurname(businessOwnerDTO.getOwnerSurname());
        existingBusinessOwner.setPhone(businessOwnerDTO.getPhone());
        existingBusinessOwner.setEmail(businessOwnerDTO.getEmail());
        existingBusinessOwner.setAddress(businessOwnerDTO.getAddress());
        existingBusinessOwner.setCity(businessOwnerDTO.getCity());
        existingBusinessOwner.setDistrict(businessOwnerDTO.getDistrict());
        existingBusinessOwner.setPostalCode(businessOwnerDTO.getPostalCode());
        existingBusinessOwner.setTaxNumber(businessOwnerDTO.getTaxNumber());
        existingBusinessOwner.setServices(businessOwnerDTO.getServices());
        existingBusinessOwner.setWorkingHours(businessOwnerDTO.getWorkingHours());
        existingBusinessOwner.setDescription(businessOwnerDTO.getDescription());
        
        BusinessOwner updatedBusinessOwner = businessOwnerRepository.save(existingBusinessOwner);
        return convertToDTO(updatedBusinessOwner);
    }
    
    @Transactional(readOnly = true)
    public BusinessOwnerDTO getBusinessOwnerById(Long id) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İşletme sahibi bulunamadı"));
        return convertToDTO(businessOwner);
    }
    
    @Transactional(readOnly = true)
    public BusinessOwnerDTO getBusinessOwnerByPhone(String phone) {
        BusinessOwner businessOwner = businessOwnerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Bu telefon numarası ile kayıtlı işletme sahibi bulunamadı"));
        return convertToDTO(businessOwner);
    }
    
    @Transactional(readOnly = true)
    public BusinessOwnerDTO getBusinessOwnerByEmail(String email) {
        BusinessOwner businessOwner = businessOwnerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bu e-posta adresi ile kayıtlı işletme sahibi bulunamadı"));
        return convertToDTO(businessOwner);
    }
    
    @Transactional(readOnly = true)
    public List<BusinessOwnerDTO> getAllBusinessOwners() {
        List<BusinessOwner> businessOwners = businessOwnerRepository.findAllOrderByCreatedAtDesc();
        return businessOwners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<BusinessOwnerDTO> searchBusinessOwners(String query) {
        List<BusinessOwner> businessOwners = businessOwnerRepository.findByBusinessNameContainingIgnoreCaseOrderByCreatedAtDesc(query);
        return businessOwners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<BusinessOwnerDTO> getBusinessOwnersByCity(String city) {
        List<BusinessOwner> businessOwners = businessOwnerRepository.findByCityIgnoreCaseOrderByCreatedAtDesc(city);
        return businessOwners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<BusinessOwnerDTO> getBusinessOwnersByCityAndDistrict(String city, String district) {
        List<BusinessOwner> businessOwners = businessOwnerRepository.findByCityIgnoreCaseAndDistrictIgnoreCaseOrderByCreatedAtDesc(city, district);
        return businessOwners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public long getBusinessOwnerCount() {
        return businessOwnerRepository.count();
    }
    
    public void deleteBusinessOwner(Long id) {
        if (!businessOwnerRepository.existsById(id)) {
            throw new RuntimeException("İşletme sahibi bulunamadı");
        }
        businessOwnerRepository.deleteById(id);
    }
    
    // Helper methods for conversion
    private BusinessOwner convertToEntity(BusinessOwnerDTO dto) {
        BusinessOwner businessOwner = new BusinessOwner();
        businessOwner.setBusinessName(dto.getBusinessName());
        businessOwner.setOwnerName(dto.getOwnerName());
        businessOwner.setOwnerSurname(dto.getOwnerSurname());
        businessOwner.setPhone(dto.getPhone());
        businessOwner.setEmail(dto.getEmail());
        businessOwner.setAddress(dto.getAddress());
        businessOwner.setCity(dto.getCity());
        businessOwner.setDistrict(dto.getDistrict());
        businessOwner.setPostalCode(dto.getPostalCode());
        businessOwner.setTaxNumber(dto.getTaxNumber());
        businessOwner.setServices(dto.getServices());
        businessOwner.setWorkingHours(dto.getWorkingHours());
        businessOwner.setDescription(dto.getDescription());
        return businessOwner;
    }
    
    private BusinessOwnerDTO convertToDTO(BusinessOwner businessOwner) {
        BusinessOwnerDTO dto = new BusinessOwnerDTO();
        dto.setId(businessOwner.getId());
        dto.setBusinessName(businessOwner.getBusinessName());
        dto.setOwnerName(businessOwner.getOwnerName());
        dto.setOwnerSurname(businessOwner.getOwnerSurname());
        dto.setPhone(businessOwner.getPhone());
        dto.setEmail(businessOwner.getEmail());
        dto.setAddress(businessOwner.getAddress());
        dto.setCity(businessOwner.getCity());
        dto.setDistrict(businessOwner.getDistrict());
        dto.setPostalCode(businessOwner.getPostalCode());
        dto.setTaxNumber(businessOwner.getTaxNumber());
        dto.setServices(businessOwner.getServices());
        dto.setWorkingHours(businessOwner.getWorkingHours());
        dto.setDescription(businessOwner.getDescription());
        dto.setCreatedAt(businessOwner.getCreatedAt());
        dto.setUpdatedAt(businessOwner.getUpdatedAt());
        return dto;
    }
}