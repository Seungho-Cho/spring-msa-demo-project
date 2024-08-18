package com.csh.order_service.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SqsServiceTest {

    @Autowired
    private SqsService sqsService;


    @Test
    @Disabled
    void testSendMessage() {
        String testMessage = "Hello SQS";
        sqsService.sendMessage(testMessage);
    }
}
