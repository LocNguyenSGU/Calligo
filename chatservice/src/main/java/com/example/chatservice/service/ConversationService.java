package com.example.chatservice.service;

import com.example.chatservice.dto.request.ConversationRequestDTO;
import com.example.chatservice.dto.response.ConversationResponse;
import com.example.chatservice.entity.Conversation;

import java.util.List;

public interface ConversationService {
    List<Conversation> getAllConversation();

    void saveConversation(ConversationRequestDTO request);

    ConversationResponse getConversationById(String idConversation);

}
