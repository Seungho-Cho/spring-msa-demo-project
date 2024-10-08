package com.csh.order_service.entity;

import com.csh.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "service_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private Long inventoryId;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String orderDate;
    private String shippingDate;

}
