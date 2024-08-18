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
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SqsService sqsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<OrderEntity> getOrderById(String id) {
        return orderRepository.findById(Long.parseLong(id));
    }

    @Transactional
    public OrderEntity createOrder(OrderEntity orderEntity) {
        //사용자 및 상품에 대한 검증 생략

        OrderEntity createdOrder = saveEntity(orderEntity);

        //sqs 전송 파트
        try {
            String orderJson = objectMapper.writeValueAsString(createdOrder);
            //sqs 예외처리 생략
            sqsService.sendMessage(orderJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return createdOrder;
    }

    /**
     * 주문 생성 테스트 목적으로만 사용
     * SQS 메시지 전송하지 않음
     *
     * @param orderEntity
     * @return saved orderEntity
     */
    public OrderEntity createOrderNotMessaging(OrderEntity orderEntity) {
        return saveEntity(orderEntity);
    }

    private OrderEntity saveEntity(OrderEntity orderEntity) {
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrder() {
        return orderRepository.findAll();
    }

    public OrderEntity updateOrderStatusById(String id, String orderStatus, String statusDate) {
        return orderRepository.findById(Long.parseLong(id))
                .map(orderEntity -> {
                    orderEntity.setOrderStatus(OrderStatus.valueOf(orderStatus));
                    orderEntity.setShippingDate(statusDate);
                    return orderRepository.save(orderEntity);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order not exist OR Order status not available"));
    }
}
