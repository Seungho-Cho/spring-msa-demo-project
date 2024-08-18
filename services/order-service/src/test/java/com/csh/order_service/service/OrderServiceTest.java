package com.csh.order_service.service;

import com.csh.order_service.entity.OrderEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Disabled("실제 전송 테스트이므로 테스트 빌드시 제외")
    void testCreateOrder() {
        OrderEntity testOrder = OrderEntity.builder()
                .inventoryId(1L)
                .userId("testUser")
                .address("just test address")
                .build();
        OrderEntity resultOrder = orderService.createOrder(testOrder);
        assertNotNull(resultOrder);
    }
}
