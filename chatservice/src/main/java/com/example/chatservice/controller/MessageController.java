package com.example.chatservice.controller;

import com.example.chatservice.dto.response.ResponseData;
import com.example.chatservice.entity.Message;
import com.example.chatservice.service.MessageService;
import com.example.commonservice.model.PageResponse;
import com.example.commonservice.model.ResponseDataMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-service/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{idConversation}")
//    @SendTo("/topic/conversation/{idConversation}")
    public List<Message> getMessages(@PathVariable String idConversation) {
        return messageService.getMessagesByConversation(idConversation);
    }

    @GetMapping("/conversation/{idConversation}")
    public ResponseEntity<?> getMessagesByIdConversation(
            @PathVariable String idConversation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        PageResponse<Message> pageResponse = messageService.getMessagesByIdConversation(
                idConversation, page, size, sortDirection
        );

        return ResponseEntity.ok(
                new ResponseDataMessage("Danh sách tin nhắn", pageResponse)
        );
    }
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
