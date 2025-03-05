package com.example.chatservice.service;

import com.example.chatservice.dto.ConversationRequestDTO;
import com.example.chatservice.entity.Conversation;

import java.util.List;

public interface ParticipantService {
    List<Conversation> getAllConversation();

    Conversation saveConversation(ConversationRequestDTO request);

    Conversation getConversationById(String idConversation);

}
