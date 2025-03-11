package com.example.kafkamsconsumer.websocket;

import avro.event.monitor.model.Transaction;
import com.example.kafkamsconsumer.mapper.TransactionMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AllTransactionsWebSocketHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private final TransactionMapper transactionMapper;

    public AllTransactionsWebSocketHandler(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @KafkaListener(
            topics = "topic-all-transactions",
            groupId = "websocket-all-value-group"
    )
    public void listenTopic(ConsumerRecord<String, Transaction> record) throws IOException {
        Transaction transaction = record.value();

        System.out.println("\nKafka Listener (All-Value Transactions) | Key: " + record.key() +
                " | Value: " + transaction +
                " | Sessions: " + sessions.size());

        if (sessions.isEmpty()) {
            System.out.println("No WebSocket sessions available.");
            return;
        }

        String json = transactionMapper.mapToJson(transaction);

        for (WebSocketSession session : sessions) {
            System.out.println("Sending to WebSocket session: " + session.getId());
            session.sendMessage(new TextMessage(json));
        }

    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        session.sendMessage(new TextMessage("Connected: " + session.getId()));
        System.out.println("WebSocket connection established: " + session.getId());
        System.out.println("Sessions size after adding: " + sessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        sessions.remove(session);
        System.out.println("WebSocket connection closed: " + session.getId());
        System.out.println("Sessions size after removal: " + sessions.size());
    }
}
