package com.csh.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    private Double price;
    private String size;
    private String color;
    private String description;

    public void copyFrom(Inventory source) {
        BeanUtils.copyProperties(source, this, "id");
    }
}
