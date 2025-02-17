package com.example.kafkamsproducer.service;

import com.example.kafkamsproducer.model.Transaction;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaProducerService {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

  //  public void sendMessage(String topic,String id, String message) {
  //      kafkaTemplate.send(topic, id, message);
  //  }

    public void sendTransaction(String topic,String id, Transaction transaction) {
        kafkaTemplate.send(topic, id, transaction);
    }

}