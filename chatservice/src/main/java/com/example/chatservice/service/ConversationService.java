package com.example.chatservice.service;

import com.example.chatservice.dto.request.AddParticipantRequestDTO;
import com.example.chatservice.dto.request.UpdateParticipantRequestDTO;
import com.example.chatservice.entity.Conversation;
import com.example.commonservice.model.FriendshipCreatedEvent;
import com.example.commonservice.model.FriendshipDeleteEvent;
import com.example.commonservice.model.PageResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ConversationService {
    List<Conversation> getAllConversation();
    Conversation getConversationById(String idConversation);
    PageResponse<Conversation> getConversationsByAccountId(String idAccount, int page, int size, String sortDirection);
    void addParticipant(String conversationId, AddParticipantRequestDTO request);
    void removeParticipant(String conversationId, String idAccount);
    void updateParticipantInfo(String conversationId, String participantId, UpdateParticipantRequestDTO request);
    void createConversation(FriendshipCreatedEvent friendshipCreatedEvent);
    void deleteConversation(FriendshipDeleteEvent friendshipDeleteEvent);
    void updateLastMessageInfo(String conversationId, String content, LocalDateTime lastTime);
}
