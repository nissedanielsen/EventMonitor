package com.example.kafkamsconsumer.websocket;


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
public class HighValueTransactionsWebSocketHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


    @KafkaListener(topics = "topic-high-value-transactions", groupId = "websocket-high-value-group")
    public void listenTopic1(ConsumerRecord<String, String> record) throws IOException {
        System.out.println("\nKafka Listener (High-Value Transactions) | Key: " + record.key() + " | Value: " + record.value() + " | Sessions: " + sessions.size());
        if (sessions.isEmpty()) {
            System.out.println("No WebSocket sessions available.");
            return;
        }

        for (WebSocketSession session : sessions) {
            System.out.println("Sending to WebSocket session: " + session.getId());
            session.sendMessage(new TextMessage(record.value()));
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

