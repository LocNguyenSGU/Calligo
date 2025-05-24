package com.example.chatservice.repository;

import com.example.chatservice.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByIdConversation(String idConversasion);
    void deleteByIdConversation(String idConversation);
}
