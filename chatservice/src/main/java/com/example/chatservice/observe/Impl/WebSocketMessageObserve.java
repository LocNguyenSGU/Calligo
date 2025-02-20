package com.example.chatservice.observe.Impl;

import com.example.chatservice.entity.Message;
import com.example.chatservice.observe.MessageObserve;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.WebSocketSession;
public class WebSocketMessageObserve implements MessageObserve {
    private final WebSocketSession webSocketSession;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String destination;

    public WebSocketMessageObserve(WebSocketSession webSocketSession, SimpMessagingTemplate simpMessagingTemplate, String destination) {
        this.webSocketSession = webSocketSession;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.destination = destination;
    }

    @Override
    public void onMessageReceived(Message message) {
        if (webSocketSession.isOpen()) {
            simpMessagingTemplate.convertAndSend(destination, message);
        }
    }
}
