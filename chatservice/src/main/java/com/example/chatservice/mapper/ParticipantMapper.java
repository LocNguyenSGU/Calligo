package com.example.chatservice.mapper;

import com.example.chatservice.dto.request.ParticipantRequestDTO;
import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.entity.Participant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    Participant toParticipant(ParticipantRequestDTO request);

    ParticipantResponse toParticipantResponse(Participant participant);


}
