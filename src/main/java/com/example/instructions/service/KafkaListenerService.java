package com.example.instructions.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerService {
    
    @KafkaListener(topics = "instructions", groupId = "instructions-group")
    public void listen(String message) {
        System.out.println("Kafka Message: " + message);
    }

}
