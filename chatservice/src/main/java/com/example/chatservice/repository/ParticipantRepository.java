package com.example.chatservice.repository;

import com.example.chatservice.entity.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParticipantRepository extends MongoRepository<Participant, String> {
}
