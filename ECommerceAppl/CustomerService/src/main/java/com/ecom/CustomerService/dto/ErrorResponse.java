package com.ecom.CustomerService.dto;

public class ErrorResponse {
    private String message;
    private String timestamp;

    // getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
