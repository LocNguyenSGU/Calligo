package com.example.chatservice.controller;

import com.example.chatservice.entity.Message;
import com.example.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-service/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{idConversation}")
    @SendTo("/topic/conversation/{idConversation}")
    public List<Message> getMessages(@PathVariable String idConversation) {
        return messageService.getMessagesByConversation(idConversation);
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
