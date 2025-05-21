package com.example.chatservice.service.Impl;

import com.example.chatservice.entity.Message;
import com.example.chatservice.repository.MessageRepository;
import com.example.chatservice.service.MessageService;
import com.example.commonservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        savedMessage.setTimeSent(LocalDateTime.now());
        savedMessage.setTimeUpdate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public Message updateMessage(Message message, String idMessage) {
        Message message1 = messageRepository.findById(idMessage).orElseThrow(() -> new ResourceNotFoundException("Message not found: " + idMessage));
        message1.setAttachments(message.getAttachments());
        return null;
    }
}
