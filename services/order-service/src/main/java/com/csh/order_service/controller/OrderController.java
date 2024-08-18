package com.csh.order_service.controller;

import com.csh.order_service.entity.OrderEntity;
import com.csh.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/status/{orderId}")
    public OrderEntity changeOrderStatus(@PathVariable String orderId, @RequestBody Map<String, String> requestBody) {
        String orderStatus = requestBody.get("orderStatus");
        String shippingDate = requestBody.get("shippingDate");
        return orderService.updateOrderStatusById(orderId, orderStatus, shippingDate);
    }
}
