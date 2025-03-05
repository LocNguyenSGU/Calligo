package com.example.chatservice.mapper;

import com.example.chatservice.dto.request.ConversationRequestDTO;
import com.example.chatservice.entity.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    Conversation toConversation(ConversationRequestDTO request);


}
