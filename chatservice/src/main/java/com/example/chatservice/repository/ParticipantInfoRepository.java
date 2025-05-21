package com.example.chatservice.repository;

import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.entity.ParticipantInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ParticipantInfoRepository extends MongoRepository<ParticipantInfo, String> {
//    ParticipantResponse findByIdParticipant(String idParticipant);
//    List<ParticipantInfo> findByIdAccount(String idAccount);
//    List<ParticipantInfo> findByIdConversation(String idConversation);

}
