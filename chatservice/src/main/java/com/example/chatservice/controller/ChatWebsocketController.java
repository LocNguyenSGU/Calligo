package com.example.chatservice.controller;

import com.example.chatservice.entity.Message;
import com.example.chatservice.observe.Impl.WebSocketMessageObserve;
import com.example.chatservice.observe.MessageObserve;
import com.example.chatservice.observe.MessageSubject;
import com.example.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;


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
    @SubscribeMapping("/topic/conversation/{idConversation}")
    public void addObserver(@DestinationVariable Integer idConversation, WebSocketSession socketSession) {
        MessageObserve observer = new WebSocketMessageObserve(socketSession, simpMessagingTemplate, "/topic/conversation/" + idConversation);
        messageSubject.addObserver(idConversation, observer);
    }

    // Khi client disconnect, xóa observer khỏi danh sách
    @MessageMapping("/disconnect/{idConversation}")
    public void removeObserver(@DestinationVariable Integer idConversation, WebSocketSession socketSession) {
        messageSubject.removeObserver(idConversation, new WebSocketMessageObserve(socketSession, simpMessagingTemplate, "/topic/conversation/" + idConversation));
    }
    @MessageMapping("/send/{idConversation}")
    public void receiveMessage(@DestinationVariable Integer idConversation, Message message) {
        messageService.saveMessage(message);
        sendMessage(idConversation, message);
    }

    public void sendMessage(Integer idConversation, Message message) {
        messageSubject.notifyObservers(idConversation, message);
    }
}
