package com.example.kafkamsconsumer.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private String senderId;
    private String receiverId;
    private double amount;
}

