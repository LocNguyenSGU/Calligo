package com.example.chatservice.config;

import com.example.chatservice.ws.DataHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đây là endpoint WebSocket cho client kết nối
        registry.addEndpoint("/ws-chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/topic" là nơi gửi tin nhắn tới nhiều client
        registry.enableSimpleBroker("/topic");
        // "/app" là nơi client gửi tin nhắn tới server
        registry.setApplicationDestinationPrefixes("/app");
    }
}

