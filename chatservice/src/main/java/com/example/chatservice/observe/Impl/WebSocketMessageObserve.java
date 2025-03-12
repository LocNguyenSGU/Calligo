package com.example.chatservice.observe.Impl;

import com.example.chatservice.entity.Message;
import com.example.chatservice.observe.MessageObserve;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebSocketMessageObserve implements MessageObserve {
    private final String socketSessionId;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String destination;

    public WebSocketMessageObserve(String socketSessionId, SimpMessagingTemplate simpMessagingTemplate, String destination) {
        this.socketSessionId = socketSessionId;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.destination = destination;
    }

    @Override
    public void onMessageReceived(Message message) {
        if (socketSessionId != null) {
            System.out.println("📨 Gửi tin nhắn qua WebSocket: " + message.getContent());
            simpMessagingTemplate.convertAndSend(destination, message);
        }
    }
}
