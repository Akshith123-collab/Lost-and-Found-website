package com.ssn.registration.service;

import com.ssn.registration.dto.CreateItemRequest;
import com.ssn.registration.dto.UpdateItemRequest;
import com.ssn.registration.exception.NotFoundException;
import com.ssn.registration.model.Item;
import com.ssn.registration.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ItemService {

    private final ItemRepository repo;

    @Value("${app.upload.dir:${user.dir}/uploads}")
    private String uploadDir;

    private final Pattern phonePattern = Pattern.compile("^[0-9+\\-\\s]{6,20}$");
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public List<Item> listByUser(String username) {
        return repo.findByCreatedBy(username);
    }

 // inside ItemService.java (only the create method shown/updated)
    public Item create(CreateItemRequest req, String createdBy) {
        String contact = req.getReporterContact();
        if (!isValidContact(contact)) {
            throw new IllegalArgumentException("Reporter contact must be a valid email or phone number");
        }

        Item it = new Item();
        it.setTitle(req.getTitle().trim());
        it.setDescription(req.getDescription().trim());
        it.setLocation(req.getLocation().trim());
        it.setReporterName(req.getReporterName().trim());
        it.setReporterContact(contact.trim());
        it.setFound(req.isFound());
        it.setStatus("LOST");

        Instant now = Instant.now();
        it.setCreatedAt(now);
        it.setUpdatedAt(now);

        // set creator username provided by controller
        it.setCreatedBy(createdBy);

        // 🖼 Handle image upload if CreateItemRequest carries MultipartFile (depends on your DTO)
        MultipartFile image = req.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.createDirectories(path.getParent());
                image.transferTo(path.toFile());
                it.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        return repo.save(it);
    }

    public Item getById(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Item not found with id: " + id));
    }

    public List<Item> list(Boolean found) {
        if (found == null) {
            return repo.findAll();
        } else {
            return repo.findByFound(found);
        }
    }

    public List<Item> search(String q) {
        if (!StringUtils.hasText(q)) {
            return repo.findAll();
        }
        return repo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q);
    }

    // ✅ Updated update() with role-based logic
    public Item update(String id, UpdateItemRequest req, String username, String role) {
        Item existing = getById(id);

        // 🛑 Authorization check
        if (!existing.getCreatedBy().equals(username) && !"ADMIN".equalsIgnoreCase(role)) {
            throw new RuntimeException("You are not authorized to edit this item");
        }

        boolean changed = false;
        if (req.getTitle() != null) { existing.setTitle(req.getTitle()); changed = true; }
        if (req.getDescription() != null) { existing.setDescription(req.getDescription()); changed = true; }
        if (req.getLocation() != null) { existing.setLocation(req.getLocation()); changed = true; }
        if (req.getReporterContact() != null) {
            if (!isValidContact(req.getReporterContact())) {
                throw new IllegalArgumentException("Reporter contact must be a valid email or phone number");
            }
            existing.setReporterContact(req.getReporterContact());
            changed = true;
        }
        if (req.getReporterName() != null) { existing.setReporterName(req.getReporterName()); changed = true; }
        if (req.getStatus() != null) { existing.setStatus(req.getStatus()); changed = true; }

        if (changed) existing.setUpdatedAt(Instant.now());
        return repo.save(existing);
    }

    // ✅ Updated delete() with role-based logic
    public void delete(String id, String username, String role) {
        Item existing = getById(id);

        if (!existing.getCreatedBy().equals(username) && !"ADMIN".equalsIgnoreCase(role)) {
            throw new RuntimeException("You are not authorized to delete this item");
        }

        repo.delete(existing);
    }

    private boolean isValidContact(String contact) {
        if (!StringUtils.hasText(contact)) return false;
        String c = contact.trim();
        return emailPattern.matcher(c).matches() || phonePattern.matcher(c).matches();
    }

    public Item save(Item item) {
        return repo.save(item);
    }
}
