package com.example.chatservice.service;

import com.example.chatservice.dto.request.ParticipantRequestDTO;
import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.entity.Participant;

import java.util.List;

public interface ParticipantService {

    void saveParticipant(ParticipantRequestDTO request);

    ParticipantResponse getParticipantbyId(String idParticipant);

    List<Participant> getAllParticipant();

    List<ParticipantResponse> getAllParticipantByAccount(String idAccount);

}
