package com.barbershop.controller;

import com.barbershop.dto.BusinessOwnerDTO;
import com.barbershop.service.BusinessOwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/business-owners")
@CrossOrigin(origins = "http://localhost:3000")
public class BusinessOwnerController {
    
    @Autowired
    private BusinessOwnerService businessOwnerService;
    
    @PostMapping
    public ResponseEntity<?> createBusinessOwner(@Valid @RequestBody BusinessOwnerDTO businessOwnerDTO) {
        try {
            BusinessOwnerDTO createdBusinessOwner = businessOwnerService.createBusinessOwner(businessOwnerDTO);
            return ResponseEntity.ok(createdBusinessOwner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBusinessOwner(@PathVariable Long id, @Valid @RequestBody BusinessOwnerDTO businessOwnerDTO) {
        try {
            BusinessOwnerDTO updatedBusinessOwner = businessOwnerService.updateBusinessOwner(id, businessOwnerDTO);
            return ResponseEntity.ok(updatedBusinessOwner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessOwnerById(@PathVariable Long id) {
        try {
            BusinessOwnerDTO businessOwner = businessOwnerService.getBusinessOwnerById(id);
            return ResponseEntity.ok(businessOwner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getBusinessOwnerByPhone(@PathVariable String phone) {
        try {
            BusinessOwnerDTO businessOwner = businessOwnerService.getBusinessOwnerByPhone(phone);
            return ResponseEntity.ok(businessOwner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getBusinessOwnerByEmail(@PathVariable String email) {
        try {
            BusinessOwnerDTO businessOwner = businessOwnerService.getBusinessOwnerByEmail(email);
            return ResponseEntity.ok(businessOwner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<BusinessOwnerDTO>> getAllBusinessOwners() {
        List<BusinessOwnerDTO> businessOwners = businessOwnerService.getAllBusinessOwners();
        return ResponseEntity.ok(businessOwners);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<BusinessOwnerDTO>> searchBusinessOwners(@RequestParam String query) {
        List<BusinessOwnerDTO> businessOwners = businessOwnerService.searchBusinessOwners(query);
        return ResponseEntity.ok(businessOwners);
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<BusinessOwnerDTO>> getBusinessOwnersByCity(@PathVariable String city) {
        List<BusinessOwnerDTO> businessOwners = businessOwnerService.getBusinessOwnersByCity(city);
        return ResponseEntity.ok(businessOwners);
    }
    
    @GetMapping("/city/{city}/district/{district}")
    public ResponseEntity<List<BusinessOwnerDTO>> getBusinessOwnersByCityAndDistrict(
            @PathVariable String city, 
            @PathVariable String district) {
        List<BusinessOwnerDTO> businessOwners = businessOwnerService.getBusinessOwnersByCityAndDistrict(city, district);
        return ResponseEntity.ok(businessOwners);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getBusinessOwnerCount() {
        long count = businessOwnerService.getBusinessOwnerCount();
        return ResponseEntity.ok(count);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBusinessOwner(@PathVariable Long id) {
        try {
            businessOwnerService.deleteBusinessOwner(id);
            return ResponseEntity.ok(new SuccessResponse("İşletme sahibi başarıyla silindi"));
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