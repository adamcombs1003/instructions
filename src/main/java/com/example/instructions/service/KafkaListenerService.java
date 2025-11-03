package com.example.instructions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerService {

    private final Logger LOG = LoggerFactory.getLogger(KafkaListenerService.class);
    
    @KafkaListener(topics = "instructions", groupId = "instructions-group")
    public void listen(String message) {
        LOG.info("Received Message: {}", message);
    }

}
