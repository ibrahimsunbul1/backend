package com.barbershop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ")
        );
        
        ErrorResponse errorResponse = new ErrorResponse(
            errorMessage.toString(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
            org.springframework.web.servlet.resource.NoResourceFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            "Resource not found: " + ex.getResourcePath(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            "Runtime error: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            "An unexpected error occurred: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ErrorResponse {
        private String message;
        private int status;
        private LocalDateTime timestamp;
        private String path;

        public ErrorResponse(String message, int status, LocalDateTime timestamp) {
            this.message = message;
            this.status = status;
            this.timestamp = timestamp;
        }

        // Getters and Setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}