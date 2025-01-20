package com.example.chatservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketController {
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        // Xử lý message ở đây nếu cần
        return "Server nhận: " + message;
    }
}
