package com.csh.inventory_service.service;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.graphql.InventoryFilter;
import com.csh.inventory_service.repository.InventoryRepository;
import com.csh.inventory_service.repository.InventorySearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventorySearchRepository inventorySearchRepository;

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public List<Inventory> searchInventories(InventoryFilter filter) {
        return inventorySearchRepository.searchInventories(filter);
    }
}
