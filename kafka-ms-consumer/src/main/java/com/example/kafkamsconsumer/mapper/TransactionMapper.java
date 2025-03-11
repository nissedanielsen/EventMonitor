package com.example.kafkamsconsumer.mapper;

import avro.event.monitor.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionMapper {

    private final ObjectMapper objectMapper;

    public TransactionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String mapToJson(Transaction transaction) throws JsonProcessingException {
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("transactionId", transaction.getTransactionId().toString());
        transactionMap.put("senderId", transaction.getSenderId().toString());
        transactionMap.put("receiverId", transaction.getReceiverId().toString());
        transactionMap.put("amount", transaction.getAmount());

        return objectMapper.writeValueAsString(transactionMap);
    }
}