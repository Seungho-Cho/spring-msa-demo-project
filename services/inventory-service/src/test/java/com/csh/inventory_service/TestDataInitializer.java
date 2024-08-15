package com.csh.inventory_service;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.repository.InventoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestDataInitializer {

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostConstruct
    @Transactional
    public void init() {
        inventoryRepository.deleteAll();

        Inventory inventory1 = new Inventory("Product A", 10);
        Inventory inventory2 = new Inventory("Product B", 20);
        inventoryRepository.save(inventory1);
        inventoryRepository.save(inventory2);
    }
}
