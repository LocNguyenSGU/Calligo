package com.example.chatservice.service.Impl;

import com.example.chatservice.dto.request.ConversationRequestDTO;
import com.example.chatservice.dto.response.ConversationDetailDTO;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.entity.Participant;
import com.example.chatservice.mapper.ConversationMapper;
import com.example.chatservice.repository.ConversationRepository;
import com.example.chatservice.repository.ParticipantRepository;
import com.example.chatservice.service.ConversationService;
import com.example.commonservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationSeviceImp implements ConversationService {

    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ParticipantRepository participantRepository;
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


    public ConversationDetailDTO getConversationWithParticipants(String idConversation) {
        Conversation conversation = conversationRepository.findById(idConversation)
                .orElseThrow(() -> new ResourceNotFoundException("Khong co conversation voi id: " + idConversation));

        List<Participant> participants = participantRepository.findByIdConversation(idConversation);
        return new ConversationDetailDTO(conversation, participants);
    }
}
