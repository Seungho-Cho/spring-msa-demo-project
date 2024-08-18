package com.csh.order_service.service;

import com.csh.order_service.entity.OrderEntity;
import com.csh.order_service.enums.OrderStatus;
import com.csh.order_service.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SqsService sqsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public OrderEntity createOrder(OrderEntity orderEntity) {
        //사용자 및 상품에 대한 검증 생략
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        OrderEntity createdOrder = orderRepository.save(orderEntity);

        try {
            String orderJson = objectMapper.writeValueAsString(createdOrder);
            //sqs 예외처리 생략
            sqsService.sendMessage(orderJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return createdOrder;
    }

    public List<OrderEntity> getAllOrder() {
        return orderRepository.findAll();
    }
}
