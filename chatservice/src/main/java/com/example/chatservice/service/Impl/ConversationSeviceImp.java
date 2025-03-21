package com.example.chatservice.service.Impl;

import com.example.chatservice.dto.request.ConversationRequestDTO;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.mapper.ConversationMapper;
import com.example.chatservice.repository.ConversationRepository;
import com.example.chatservice.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationSeviceImp implements ConversationService {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationMapper conversationMapper;

    @Override
    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    @Override
    public void saveConversation(ConversationRequestDTO request) {
        Conversation conversation = conversationMapper.toConversation(request);
        conversationRepository.save(conversation);
    }


    @Override
    public Conversation getConversationById(String idConversation) {
        Conversation conversation = conversationRepository.findByIdConversation(idConversation);
        return conversation;
    }
}
