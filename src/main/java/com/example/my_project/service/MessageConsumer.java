package com.example.my_project.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @KafkaListener(topics = "my-topic", groupId = "demo-group")
    public void listen(String message) {
        System.out.println("📩 Received message: " + message);
    }
}