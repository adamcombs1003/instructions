package com.example.instructions.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.util.TradeTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TradeService {

    private final TradeTransformer tradeTransformer;
    private final KafkaPublisher kafkaPublisher;
    public static final String TOPIC = "instructions";

    public TradeService(TradeTransformer tradeTransformer, KafkaPublisher KafkaPublisher) {
        this.tradeTransformer = tradeTransformer;
        this.kafkaPublisher = KafkaPublisher;
    }

    public List<CanonicalTrade> processUploadedFile(MultipartFile file) throws JsonMappingException, JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        
        if(file != null && file.getContentType() != null) {
            List<CanonicalTrade> canonicalTrades = new ArrayList<>();
            if(file.getContentType().equals("text/csv")) {
                canonicalTrades = tradeTransformer.transformCsvTrades(new String(file.getBytes()));
            } else if (file.getContentType().equals("application/json")) {
                canonicalTrades = tradeTransformer.transformJsonTrades(new String(file.getBytes()));
            } else {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Only csv and json files are accepted.");
            }
            canonicalTrades.forEach(tradeInstruction -> {
                try {
                    kafkaPublisher.sendMessage(TOPIC, objectMapper.writeValueAsString(tradeInstruction));
                } catch (JsonProcessingException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to parse trade instruction.");
                }
            });
            return canonicalTrades;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found.");
        }
    }

}
