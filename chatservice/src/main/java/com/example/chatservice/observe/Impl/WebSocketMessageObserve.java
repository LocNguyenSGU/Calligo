package com.example.chatservice.observe.Impl;

import com.example.chatservice.entity.Message;
import com.example.chatservice.observe.MessageObserve;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebSocketMessageObserve implements MessageObserve {
    private final String socketSessionId;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketMessageObserve(String socketSessionId, SimpMessagingTemplate simpMessagingTemplate, String destination) {
        this.socketSessionId = socketSessionId;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessageReceived(Message message, String destination) {
        if (socketSessionId != null) {
            System.out.println("📨 Tin nhắn qua WebSocket: " + message.getContent());
            System.out.println("destination:" + destination);
            simpMessagingTemplate.convertAndSend(destination, message);
        }
    }

    @Override
    public String getSocketSessionId() {
        return socketSessionId;
    }
}
