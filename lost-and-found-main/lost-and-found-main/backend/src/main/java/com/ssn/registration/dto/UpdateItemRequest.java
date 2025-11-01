package com.ssn.registration.dto;

import jakarta.validation.constraints.Size;

public class UpdateItemRequest {
    @Size(max = 150, message = "Title must be at most 150 characters")
    private String title;

    @Size(max = 2000, message = "Description too long")
    private String description;

    private String location;
    private String reporterContact;
    private String reporterName;
    private String status; // e.g. "claimed","open","closed"

    public UpdateItemRequest() {}

    // getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getReporterContact() { return reporterContact; }
    public void setReporterContact(String reporterContact) { this.reporterContact = reporterContact; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
