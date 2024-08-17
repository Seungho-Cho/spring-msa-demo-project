package com.csh.inventory_service.repository;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.graphql.InventoryFilter;

import java.util.List;

public interface InventorySearchRepository {
    List<Inventory> searchInventories(InventoryFilter filter);
}
