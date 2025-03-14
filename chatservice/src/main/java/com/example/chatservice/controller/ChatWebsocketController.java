package com.example.chatservice.controller;

import com.example.chatservice.entity.Message;
import com.example.chatservice.observe.Impl.WebSocketMessageObserve;
import com.example.chatservice.observe.MessageObserve;
import com.example.chatservice.observe.MessageSubject;
import com.example.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatWebsocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageSubject messageSubject;
    @Autowired
    private MessageService messageService;

    public ChatWebsocketController(SimpMessagingTemplate simpMessagingTemplate, MessageSubject messageSubject) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageSubject = messageSubject;
    }

    // Khi một client subscribe vào conversation, thêm observer
    @MessageMapping("/topic/conversation/{idConversation}")
    public void addObserver(@DestinationVariable String idConversation, SimpMessageHeaderAccessor headerAccessor) {
        String socketSessionId = headerAccessor.getSessionId();
        MessageObserve observer = new WebSocketMessageObserve(socketSessionId, simpMessagingTemplate, "/topic/conversation/" + idConversation);
        messageSubject.addObserver(idConversation, observer);
    }

    // Khi client disconnect, xóa observer khỏi danh sách
    @MessageMapping("/disconnect/{idConversation}")
    public void removeObserver(@DestinationVariable String idConversation, SimpMessageHeaderAccessor headerAccessor) {
        String socketSessionId = headerAccessor.getSessionId();
        messageSubject.removeObserver(idConversation, new WebSocketMessageObserve(socketSessionId, simpMessagingTemplate, "/topic/conversation/" + idConversation));
    }

    @MessageMapping("/send/{idConversation}")
    public void receiveMessage(@DestinationVariable String idConversation, Message message) {
        messageService.saveMessage(message);
        sendMessage(idConversation, message);
    }

    public void sendMessage(String idConversation, Message message) {
        messageSubject.notifyObservers(idConversation, message);
    }
}
