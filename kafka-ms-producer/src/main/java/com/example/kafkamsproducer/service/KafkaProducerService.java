package com.example.kafkamsproducer.service;

import com.example.kafkamsproducer.model.Transaction;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaProducerService {

    private final KafkaTemplate<String, avro.event.monitor.model.Transaction> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, avro.event.monitor.model.Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(String topic,String id, Transaction transaction) {

        //send avro model to topic
        //avro schema should be registered in schema register

        avro.event.monitor.model.Transaction avroTransaction = new avro.event.monitor.model.Transaction();
        avroTransaction.setTransactionId(transaction.getTransactionId());
        avroTransaction.setAmount(transaction.getAmount());
        avroTransaction.setReceiverId(transaction.getReceiverId());
        avroTransaction.setSenderId(transaction.getSenderId());

        kafkaTemplate.send(topic, id, avroTransaction);
    }

}