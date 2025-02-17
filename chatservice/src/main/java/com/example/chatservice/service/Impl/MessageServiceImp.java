package com.example.chatservice.service.Impl;

import com.example.chatservice.entity.Message;
import com.example.chatservice.repository.MessageRepository;
import com.example.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByConversation(String idConversation) {
        return messageRepository.findByIdConversation(idConversation);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
