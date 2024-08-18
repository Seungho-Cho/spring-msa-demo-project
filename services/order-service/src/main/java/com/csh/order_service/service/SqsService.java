package com.csh.order_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsService {

    @Autowired
    private SqsClient sqsClient;
    private final String queueUrl = "https://sqs.ap-northeast-2.amazonaws.com/091012489433/msa-sqs-demo";

    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(sendMessageRequest);
    }
}
