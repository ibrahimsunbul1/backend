package com.barbershop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        
        // Get error status
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");
        String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = "An error occurred";
        }
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", statusCode);
        errorResponse.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        errorResponse.put("message", errorMessage);
        errorResponse.put("path", requestUri);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
    }
}