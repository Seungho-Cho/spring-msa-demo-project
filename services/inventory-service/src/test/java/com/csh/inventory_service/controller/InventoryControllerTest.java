package com.csh.inventory_service.controller;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        inventoryRepository.deleteAll();

        Inventory inventory1 = new Inventory("Product A", 10);
        Inventory inventory2 = new Inventory("Product B", 20);
        inventoryRepository.saveAll(Arrays.asList(inventory1, inventory2));
    }


    @Test
    @Transactional
    void testGetAllInventories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productName").value("Product A"))
                .andExpect(jsonPath("$[1].productName").value("Product B"));
    }

    @Test
    @Transactional
    void testGetInventoryById_Success() throws Exception {
        Inventory inventory = inventoryRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/{id}", inventory.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(inventory.getId().intValue())));
    }

    @Test
    @Transactional
    void testGetInventoryById_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/{id}", -1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void testCreateInventory() throws Exception {
        String newInventoryJson = "{\"productName\":\"Product C\",\"quantity\":30}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newInventoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is("Product C")))
                .andExpect(jsonPath("$.quantity", is(30)));
    }

    @Test
    @Transactional
    void testUpdateInventory_Success() throws Exception{
        Inventory inventory = inventoryRepository.findAll().get(0);
        String UpdateInventoryJson = "{\"id\":\""+inventory.getId()+"\",\"productName\":\"Product A\",\"quantity\":9}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/inventory/{id}",inventory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(UpdateInventoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(9)));

    }

    @Test
    @Transactional
    void testDeleteInventory() throws Exception {
        Inventory inventory = inventoryRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/inventory/{id}", inventory.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Inventory> deletedInventory = inventoryRepository.findById(inventory.getId());
        assertTrue(deletedInventory.isEmpty());
    }
}
