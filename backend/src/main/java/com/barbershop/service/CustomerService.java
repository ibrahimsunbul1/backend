package com.barbershop.service;

import com.barbershop.dto.CustomerDTO;
import com.barbershop.entity.Customer;
import com.barbershop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Telefon numarası kontrolü
        if (customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Bu telefon numarası zaten kayıtlı");
        }
        
        // E-posta kontrolü (eğer verilmişse)
        if (customerDTO.getEmail() != null && !customerDTO.getEmail().isEmpty() && 
            customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Bu e-posta adresi zaten kayıtlı");
        }
        
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }
    
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
        
        // Telefon numarası kontrolü (farklı müşteriye ait olmamalı)
        if (!existingCustomer.getPhone().equals(customerDTO.getPhone()) && 
            customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Bu telefon numarası zaten kayıtlı");
        }
        
        // E-posta kontrolü (farklı müşteriye ait olmamalı)
        if (customerDTO.getEmail() != null && !customerDTO.getEmail().isEmpty() &&
            !customerDTO.getEmail().equals(existingCustomer.getEmail()) &&
            customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Bu e-posta adresi zaten kayıtlı");
        }
        
        updateCustomerFields(existingCustomer, customerDTO);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(updatedCustomer);
    }
    
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
        return convertToDTO(customer);
    }
    
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
        return convertToDTO(customer);
    }
    
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAllOrderByCreatedAtDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomersByName(String name) {
        return customerRepository.findByNameContaining(name)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomersByPhone(String phone) {
        return customerRepository.findByPhoneContaining(phone)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Müşteri bulunamadı");
        }
        customerRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long getCustomerCount() {
        return customerRepository.countAllCustomers();
    }
    
    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setBirthDate(dto.getBirthDate());
        customer.setPreferredServices(dto.getPreferredServices());
        customer.setNotes(dto.getNotes());
        return customer;
    }
    
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setBirthDate(customer.getBirthDate());
        dto.setPreferredServices(customer.getPreferredServices());
        dto.setNotes(customer.getNotes());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        return dto;
    }
    
    private void updateCustomerFields(Customer customer, CustomerDTO dto) {
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setBirthDate(dto.getBirthDate());
        customer.setPreferredServices(dto.getPreferredServices());
        customer.setNotes(dto.getNotes());
    }
}