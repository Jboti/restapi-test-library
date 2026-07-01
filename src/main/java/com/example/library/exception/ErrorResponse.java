package com.example.library.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

    private Integer status;
    private String message;
    private LocalDateTime timestamp;

    // For validation errors
    private Map<String, String> errors;


    public ErrorResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(Integer status, String message, Map<String,String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public Integer getStatus() { return status; }

    public String getMessage() { return message; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public Map<String, String> getErrors() { return errors; }
}
