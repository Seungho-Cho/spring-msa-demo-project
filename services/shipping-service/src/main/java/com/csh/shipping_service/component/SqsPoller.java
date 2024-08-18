package com.csh.shipping_service.component;

import com.csh.shipping_service.service.SqsReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SqsPoller {

    @Autowired
    private SqsReceiveService sqsReceiveService;

    @Scheduled(fixedRate = 60000)
    public void pollMessage() {
        sqsReceiveService.receiveMessges();
    }
}
