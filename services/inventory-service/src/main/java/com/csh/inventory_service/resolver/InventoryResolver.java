package com.csh.inventory_service.resolver;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.graphql.InventoryFilter;
import com.csh.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class InventoryResolver {

    @Autowired
    private InventoryService inventoryService;

    @QueryMapping
    public List<Inventory> searchInventories(@Argument InventoryFilter filter) {
        return inventoryService.searchInventories(filter);
    }
}
