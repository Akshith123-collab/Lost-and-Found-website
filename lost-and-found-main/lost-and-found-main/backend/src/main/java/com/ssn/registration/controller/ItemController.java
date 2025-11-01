package com.ssn.registration.controller;

import com.ssn.registration.dto.CreateItemRequest;
import com.ssn.registration.dto.UpdateItemRequest;
import com.ssn.registration.model.Item;
import com.ssn.registration.service.ItemService;
import com.ssn.registration.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemController {

    private final ItemService service;
    private final JwtUtil jwtService;

    public ItemController(ItemService service, JwtUtil jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    // ✅ Create a new item — attach logged-in user’s username
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Item> createItem(
            @ModelAttribute CreateItemRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token = authHeader.substring(7); // remove "Bearer "
            String username = jwtService.extractUsername(token);

            // pass username directly into service.create()
            Item savedItem = service.create(req, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Get all items (optional filter by found)
    @GetMapping
    public ResponseEntity<List<Item>> listItems(@RequestParam(required = false) Boolean found) {
        List<Item> list = service.list(found);
        return ResponseEntity.ok(list);
    }

    // ✅ Get a single item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id) {
        Item it = service.getById(id);
        return ResponseEntity.ok(it);
    }

    // ✅ Get only logged-in user's items (admin sees all)
    @GetMapping("/my")
    public ResponseEntity<List<Item>> getMyItems(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);

            List<Item> items;
            if ("ADMIN".equalsIgnoreCase(role)) {
                items = service.list(null); // Admin sees all items
            } else {
                items = service.listByUser(username);
            }

            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Search endpoint
    @GetMapping("/search")
    public ResponseEntity<List<Item>> search(@RequestParam(required = false) String q) {
        List<Item> result = service.search(q);
        return ResponseEntity.ok(result);
    }

    // ✅ Update item (PUT used instead of PATCH)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable String id,
            @RequestBody UpdateItemRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);

            Item updated = service.update(id, req, username, role);
            return ResponseEntity.ok(updated);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating item");
        }
    }

    // ✅ Delete item (creator or admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(
            @PathVariable String id,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);

            service.delete(id, username, role);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting item");
        }
    }
}
