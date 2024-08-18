package com.csh.inventory_service.service;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

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
    void testGetInventoryById_Success() {
        Optional<Inventory> result = inventoryService.getInventoryById(1L);

        assertTrue(result.isPresent());
        assertEquals("Product A", result.get().getProductName());
        assertEquals(10, result.get().getQuantity());
    }

    @Test
    @Transactional
    void testGetInventoryById_NotFound() {
        Optional<Inventory> result = inventoryService.getInventoryById(-1L);

        assertFalse(result.isPresent());
    }

    @Test
    @Transactional
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
    @Transactional
    void testDelteInventory() {
        Optional<Inventory> result = inventoryService.getInventoryById(1L);
        assertTrue(result.isPresent());

        inventoryService.deleteInventory(1L);
        result = inventoryService.getInventoryById(1L);
        assertFalse(result.isPresent());
    }
}
