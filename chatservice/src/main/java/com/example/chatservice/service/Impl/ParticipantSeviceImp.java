package com.example.chatservice.service.Impl;

import com.example.chatservice.dto.ConversationRequestDTO;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.repository.ConversationRepository;
import com.example.chatservice.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantSeviceImp implements ConversationService {

    @Autowired
    ConversationRepository conversationRepository;

    @Override
    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    @Override
    public Conversation saveConversation(ConversationRequestDTO request) {
        Conversation conversation = new Conversation();
        conversation.setType(request.getType());
        conversation.setName(request.getName());
        conversation.setAvatar(request.getAvatar());
        conversation.setDateCreate(request.getDateCreate());
        conversation.setIdLastMessage(request.getIdLastMessage());
        conversation.setNumberMember(request.getNumberMember());
        return conversationRepository.save(conversation);
    }


    @Override
    public Conversation getConversationById(String idConversation) {
        return conversationRepository.findByIdConversation(idConversation);
    }
}
