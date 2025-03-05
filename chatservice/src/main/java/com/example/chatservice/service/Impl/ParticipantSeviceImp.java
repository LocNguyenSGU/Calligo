package com.example.chatservice.service.Impl;

import com.example.chatservice.dto.request.ParticipantRequestDTO;
import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.entity.Participant;
import com.example.chatservice.mapper.ParticipantMapper;
import com.example.chatservice.repository.ParticipantRepository;
import com.example.chatservice.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantSeviceImp implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantMapper participantMapper;

    @Override
    public void saveParticipant(ParticipantRequestDTO request) {
        Participant participant = participantMapper.toParticipant(request);

        participantRepository.save(participant);

    }

    @Override
    public ParticipantResponse getParticipantbyId(String idParticipant) {
        Participant participant = participantRepository.findByIdParticipant(idParticipant);
        ParticipantResponse response = participantMapper.toParticipantResponse(participant);
        return response;
    }

    @Override
    public List<Participant> getAllParticipant() {

        return participantRepository.findAll();
    }


}
