package com.example.instructions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaPublisher {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger LOG = LoggerFactory.getLogger(KafkaPublisher.class);

    public KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        CompletableFuture<SendResult<String, String>> sendResultCompletableFuture = kafkaTemplate.send(topic, message);
        SendResult<String, String> sendResult;
        try {
            sendResult = sendResultCompletableFuture.get();
            LOG.info("Message '{}' sent successfully to topic {}",
                sendResult.getProducerRecord().value(),
                sendResult.getProducerRecord().topic()
            );
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Error occurred while sending kafka message", e);
        }
    }
}
