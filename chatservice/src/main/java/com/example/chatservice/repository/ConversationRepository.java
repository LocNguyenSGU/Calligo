package com.example.chatservice.repository;

import com.example.chatservice.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {

    Conversation findByIdConversation(String idConversation);

}
