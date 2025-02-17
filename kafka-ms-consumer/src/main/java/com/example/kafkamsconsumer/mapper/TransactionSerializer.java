package com.example.kafkamsconsumer.mapper;

import com.example.kafkamsconsumer.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;


public class TransactionSerializer implements Serializer<Transaction> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Transaction data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Serialization error", e);
        }
    }
}
