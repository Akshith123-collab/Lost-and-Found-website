package com.ssn.registration.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Document(collection = "items")
public class Item {

    @Id
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, message = "Title must have at least 3 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, message = "Description must have at least 10 characters")
    private String description;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @NotBlank(message = "Reporter name cannot be blank")
    private String reporterName;

    @NotBlank(message = "Reporter contact cannot be blank")
    private String reporterContact;

    private boolean found; // true => found item, false => lost item
    private String status; // e.g., "open", "claimed", "closed"

    private Instant createdAt;
    private Instant updatedAt;

    private String imageUrl; // path or URL to the uploaded image

    // 🆕 Field to link the item to the logged-in user
    private String createdBy;

    // --- Constructors ---

    public Item() {
        this.status = "open";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Item(String title, String description, String location,
                String reporterName, String reporterContact) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.reporterName = reporterName;
        this.reporterContact = reporterContact;
        this.found = false;
        this.status = "open";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // --- Getters & Setters ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    // Helper to update timestamp
    public void touch() {
        this.updatedAt = Instant.now();
    }
}
