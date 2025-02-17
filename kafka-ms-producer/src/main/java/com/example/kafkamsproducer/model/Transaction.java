package com.example.kafkamsproducer.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @NotNull(message = "Transaction ID cannot be null")
    @Size(min = 1, message = "Transaction ID cannot be empty")
    private String transactionId;

    @NotNull(message = "Sender ID cannot be null")
    @Size(min = 1, message = "Sender ID cannot be empty")
    private String senderId;

    @NotNull(message = "Receiver ID cannot be null")
    @Size(min = 1, message = "Receiver ID cannot be empty")
    private String receiverId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @Override
    public String toString() {
        return String.format("Transaction[transactionId=%s, senderId=%s, receiverId=%s, amount=%.2f]",
                transactionId, senderId, receiverId, amount);
    }
}
