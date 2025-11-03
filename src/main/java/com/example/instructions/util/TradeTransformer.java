package com.example.instructions.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.model.PlatformTrade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TradeTransformer {

    private final static String LINE_SEPARATOR = "\r\n";
    private final static int LAST_FOUR_DIGITS = 4;

    public List<CanonicalTrade> transformCsvTrades(String fileContent) {
        List<String> csvLines = Arrays.asList(fileContent.split(LINE_SEPARATOR));
        List<CanonicalTrade> transformedCanonicalTrades = new ArrayList<>();
        csvLines.forEach(csvLine -> {
            List<String> csvLineValues = Arrays.asList(csvLine.split(","));
            CanonicalTrade canonicalTrade = new CanonicalTrade(
                csvLineValues.get(0),
                new PlatformTrade(
                    csvLineValues.get(1),
                    csvLineValues.get(2),
                    csvLineValues.get(3),
                    Integer.parseInt(csvLineValues.get(4)),
                    csvLineValues.get(5)
                )
            );
            transformedCanonicalTrades.add(transformCanonicalTrade(canonicalTrade));
        });
        return transformedCanonicalTrades;
    }

    public List<CanonicalTrade> transformJsonTrades(String jsonFileContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CanonicalTrade> canonicalTrades = objectMapper.readValue(jsonFileContent, new TypeReference<>() {});
        List<CanonicalTrade> transformedCanonicalTrades = new ArrayList<>();
        canonicalTrades.forEach(trade -> {
            transformedCanonicalTrades.add(transformCanonicalTrade(trade));
        });
        return transformedCanonicalTrades;
    }

    private CanonicalTrade transformCanonicalTrade(CanonicalTrade canonicalTrade) {
        String account_number = normalizeAccountNumber(canonicalTrade.trade().account());
        String security_id = canonicalTrade.trade().security().toUpperCase();
        String trade_type;
        switch (canonicalTrade.trade().type()){
            case "B" -> trade_type = "Buy";
            case "S" -> trade_type = "Sell";
            default -> trade_type = "Unknown Instruction";
        }
        return new CanonicalTrade(
            canonicalTrade.platform_id(),
            new PlatformTrade(
                account_number,
                security_id,
                trade_type,
                canonicalTrade.trade().amount(),
                canonicalTrade.trade().timestamp()
            )
        );
    }

    private String normalizeAccountNumber(String account) {
        if(account.length() > LAST_FOUR_DIGITS){
            return account.substring(0, account.length() - LAST_FOUR_DIGITS)
                .replaceAll(".", "*") + account.substring(account.length() - LAST_FOUR_DIGITS);
        } else {
            return account;
        }
    }
}
