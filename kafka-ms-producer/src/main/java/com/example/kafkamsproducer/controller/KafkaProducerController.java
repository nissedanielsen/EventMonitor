package com.example.kafkamsproducer.controller;

import com.example.kafkamsproducer.model.Transaction;
import com.example.kafkamsproducer.service.KafkaProducerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@EnableScheduling
public class KafkaProducerController {

    private final KafkaProducerService kafkaProducerService;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public KafkaProducerController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    // Scheduled task to send random messages every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void sendTransactionScheduler() {
        double randomValue = Math.random() * 5000;

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .senderId(RandomStringUtils.randomAlphanumeric(8))
                .receiverId(RandomStringUtils.randomAlphanumeric(8))
                .amount(Math.round(randomValue * 100.0) / 100.0)
                .build();

        kafkaProducerService.sendTransaction(topicName, transaction.getTransactionId(), transaction);
        System.out.printf("Message sent to: %s, id: %s message: %s%n", topicName, transaction.getTransactionId(), transaction.toString());
    }

    @PostMapping("/sendTransaction")
    public String sendTransaction(@Valid @RequestBody Transaction transaction) {
        kafkaProducerService.sendTransaction(topicName, transaction.getTransactionId(), transaction);
        System.out.printf("Manual transaction sent to: %s, id: %s message: %s%n", topicName, transaction.getTransactionId(), transaction.toString());
        return "Transaction sent successfully!";
    }

    // Ping endpoint for health check
    @GetMapping("/ping")
    public String ping() {
        System.out.println("ping");
        return "pong";
    }

}
