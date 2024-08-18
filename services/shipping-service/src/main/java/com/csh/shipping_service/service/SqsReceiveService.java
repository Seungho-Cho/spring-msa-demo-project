package com.csh.shipping_service.service;

import com.csh.shipping_service.enums.OrderStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SqsReceiveService {

    @Autowired
    private SqsClient sqsReceiveClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${aws.queueUrl}")
    private String queueUrl;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public void receiveMessges() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(10)
                .build();

        List<Message> messageList = sqsReceiveClient.receiveMessage(receiveMessageRequest).messages();

        for (Message message : messageList) {
            try{
                Map<String, String> messageMap = objectMapper.readValue(message.body(), new TypeReference<Map<String, String>>() {});
                updateOrderStatusShipped(messageMap.get("orderId"));
                deleteMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                //sqs 메시지 변환/읽기 실패시 처리
            }

        }
    }

    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

        sqsReceiveClient.deleteMessage(deleteRequest);
    }

    public void updateOrderStatusShipped(String orderId) {
        String url = orderServiceUrl + "/api/order/status/" + orderId;
        Map<String, String> request = new HashMap<>();
        request.put("orderStatus", OrderStatus.SHIPPED.toString());
        request.put("shippingDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        restTemplate.postForObject(url, request, Void.class);
    }


}
