package com.example.chatservice.repository;

import com.example.chatservice.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Conversation findByIdConversation(String idConversation);
    @Query("{ 'participantInfos.idAccount': ?0 }")
    Page<Conversation> findByParticipantInfos_IdAccount(String idAccount, Pageable pageable);

}
