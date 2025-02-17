package com.example.kafkamsconsumer.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(allTransactionsHandler(), "/ws/transactions-all").setAllowedOrigins("*");
        registry.addHandler(highValueTransactionsHandler(), "/ws/transactions-high-value").setAllowedOrigins("*");
    }

    @Bean
    public AllTransactionsWebSocketHandler allTransactionsHandler() {
        return new AllTransactionsWebSocketHandler();
    }

    @Bean
    public HighValueTransactionsWebSocketHandler highValueTransactionsHandler() {
        return new HighValueTransactionsWebSocketHandler();
    }
}