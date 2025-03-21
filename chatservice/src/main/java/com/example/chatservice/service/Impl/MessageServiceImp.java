package com.example.chatservice.service.Impl;

import com.example.chatservice.entity.Message;
import com.example.chatservice.repository.MessageRepository;
import com.example.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public List<Message> getMessagesByConversation(String idConversation) {
        return messageRepository.findByIdConversation(idConversation);
    }

    public Message saveMessage(Message message) {
        Message savedMessage = messageRepository.save(message);

        // Gửi tin nhắn tới topic "/topic/conversation/{idConversation}"
        //simpMessagingTemplate.convertAndSend("/topic/conversation/" + message.getIdConversation(), savedMessage);

        return messageRepository.save(message);
    }
}
