package com.example.chatservice.controller;

import com.example.chatservice.entity.Message;
import com.example.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{idConversation}")
    public List<Message> getMessages(@PathVariable String idConversation) {
        return messageService.getMessagesByConversation(idConversation);
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
