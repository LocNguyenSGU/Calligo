package com.example.chatservice.service.Impl;

import com.example.chatservice.entity.Conversation;
import com.example.chatservice.entity.Message;
import com.example.chatservice.repository.MessageRepository;
import com.example.chatservice.service.ConversationService;
import com.example.chatservice.service.MessageService;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.model.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public PageResponse<Message> getMessagesByIdConversation(String idConversation, int page, int size, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by("timeSent").ascending() :
                Sort.by("timeSent").descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Message> pageResult = messageRepository.findByIdConversation(idConversation, pageable);
        return new PageResponse<>(pageResult);
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

    @Override
    public void deleteByIdConversation(String idConversation) {
        messageRepository.deleteByIdConversation(idConversation);
    }
}
