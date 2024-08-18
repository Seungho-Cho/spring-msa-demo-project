package com.csh.order_service.controller;

import com.csh.order_service.entity.OrderEntity;
import com.csh.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderEntity> getAllOrder() {
        return orderService.getAllOrder();
    }

    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderEntity orderEntity) {
        return orderService.createOrder(orderEntity);
    }

    @PostMapping("/status/{id}")
    public OrderEntity changeOrderStatus(@PathVariable String orderId, @RequestParam String status, @RequestParam String shippingDate ) {
        return orderService.updateOrderStatusById(orderId,status,shippingDate);
    }
}
