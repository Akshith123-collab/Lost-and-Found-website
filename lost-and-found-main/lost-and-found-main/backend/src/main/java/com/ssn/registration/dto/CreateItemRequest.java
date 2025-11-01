package com.ssn.registration.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateItemRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must be at most 150 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description too long")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Reporter name is required")
    private String reporterName;

    // allow either email or phone; for simplicity, we validate as email OR digits. We'll validate in service
    @NotBlank(message = "Reporter contact is required")
    private String reporterContact;

    private boolean found; // true if this is a found item
    private MultipartFile image; 
    public CreateItemRequest() {}
    

    // getters & setters ...
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getReporterContact() { return reporterContact; }
    public void setReporterContact(String reporterContact) { this.reporterContact = reporterContact; }

    public boolean isFound() { return found; }
    public void setFound(boolean found) { this.found = found; }


	public MultipartFile getImage() {
		return image;
	}


	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
