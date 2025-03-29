package com.example.chatservice.controller;

import com.example.chatservice.entity.CallMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CallController {

    private final SimpMessagingTemplate messagingTemplate;

    public CallController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/call/sendOffer")
    public void sendOffer(CallMessage message) {
        messagingTemplate.convertAndSendToUser(
                message.getTo().toString(), "/queue/call", message);
    }

    @MessageMapping("/call/sendAnswer")
    public void sendAnswer(CallMessage message) {
        messagingTemplate.convertAndSendToUser(
                message.getTo().toString(), "/queue/call", message);
    }

    @MessageMapping("/call/sendIceCandidate")
    public void sendIceCandidate(CallMessage message) {
        messagingTemplate.convertAndSendToUser(
                message.getTo().toString(), "/queue/call", message);
    }
}