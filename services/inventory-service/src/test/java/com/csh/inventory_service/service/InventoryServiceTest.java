package com.csh.inventory_service.service;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class InventoryServiceTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        inventoryRepository.deleteAll();

        Inventory inventory1 = new Inventory("Product A", 10);
        Inventory inventory2 = new Inventory("Product B", 20);
        inventoryRepository.saveAll(Arrays.asList(inventory1, inventory2));
    }

    @Test
    void testGetAllInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();

        assertEquals(2, inventoryList.size());
    }

    @Test
    void testGetInventoryById_Success() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        Optional<Inventory> result = inventoryService.getInventoryById(inventoryList.get(0).getId());

        assertTrue(result.isPresent());
        assertEquals("Product A", result.get().getProductName());
        assertEquals(10, result.get().getQuantity());
    }

    @Test
    void testGetInventoryById_NotFound() {
        Optional<Inventory> result = inventoryService.getInventoryById(-1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveInventory() {
        Inventory target = Inventory.builder()
                .productName("Product Test")
                .quantity(1)
                .build();

        Inventory result = inventoryService.saveInventory(target);

        assertNotNull(result);
        assertEquals("Product Test", result.getProductName());
        assertEquals(1, result.getQuantity());
    }

    @Test
    void testDelteInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        Optional<Inventory> result = inventoryService.getInventoryById(inventoryList.get(0).getId());
        assertTrue(result.isPresent());

        inventoryService.deleteInventory(1L);
        result = inventoryService.getInventoryById(1L);
        assertFalse(result.isPresent());
    }
}
