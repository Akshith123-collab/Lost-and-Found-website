package com.ssn.registration.dto;

import java.util.Map;

public class ApiErrorResponse {
    private String message;
    private Map<String, String> fieldErrors; // field -> message
    private int status;

    public ApiErrorResponse() {}

    public ApiErrorResponse(String message, Map<String, String> fieldErrors, int status) {
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.status = status;
    }

    // getters & setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(Map<String, String> fieldErrors) { this.fieldErrors = fieldErrors; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
