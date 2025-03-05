package com.example.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đây là endpoint WebSocket cho client kết nối
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") // dung de cho phep nhieu domain ket noi
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/topic" là nơi gửi tin nhắn tới nhiều client
        registry.enableSimpleBroker("/topic");
        // "/app" là nơi client gửi tin nhắn tới server
        registry.setApplicationDestinationPrefixes("/app");
    }
}

