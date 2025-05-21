package com.example.chatservice.repository;

import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.entity.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ParticipantRepository extends MongoRepository<Participant, String> {
    ParticipantResponse findByIdParticipant(String idParticipant);

    List<ParticipantResponse> findByIdAccount(String idAccount);
    List<Participant> findByIdConversation(String idConversation);


}
