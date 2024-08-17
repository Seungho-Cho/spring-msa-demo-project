package com.csh.inventory_service.graphql;

import lombok.Data;

@Data
public class InventoryFilter {
    private String productName;
    private Integer quantity;
    private Float price;
    private String size;
    private String color;
    private String description;
}