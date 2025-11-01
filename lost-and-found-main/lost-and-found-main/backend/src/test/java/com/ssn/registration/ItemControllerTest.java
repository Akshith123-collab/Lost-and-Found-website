package com.ssn.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssn.registration.dto.CreateItemRequest;
import com.ssn.registration.model.Item;
import com.ssn.registration.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll(); // Clean DB before each test
    }

    @Test
    void testCreateItem_Successful() throws Exception {
        CreateItemRequest req = new CreateItemRequest();
        req.setTitle("Lost Wallet");
        req.setDescription("Black leather wallet found in cafeteria");
        req.setLocation("Cafeteria");
        req.setReporterName("Darshan");
        req.setReporterContact("user@example.com");
        req.setFound(false); // Lost

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated()) // 201 CREATED
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the JSON fields in the response
                .andExpect(jsonPath("$.id", not(emptyOrNullString())))
                .andExpect(jsonPath("$.title", is("Lost Wallet")))
                .andExpect(jsonPath("$.description", containsString("Black leather wallet")))
                .andExpect(jsonPath("$.location", is("Cafeteria")))
                .andExpect(jsonPath("$.reporterName", is("Darshan")))
                .andExpect(jsonPath("$.reporterContact", is("user@example.com")))
                .andExpect(jsonPath("$.found", is(false)));
    }
}
