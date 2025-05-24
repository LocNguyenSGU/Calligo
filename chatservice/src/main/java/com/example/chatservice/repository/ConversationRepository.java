package com.example.chatservice.repository;

import com.example.chatservice.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
//    Conversation findByIdConversation(String idConversation);
    @Query("{ '_id': ?0 }")
    Optional<Conversation> findByIdConversation(String idConversation);
    @Query("{ 'participantInfos.idAccount': ?0 }")
    Page<Conversation> findByParticipantInfos_IdAccount(String idAccount, Pageable pageable);

    @Query("""
        {
          "type": "DOUBLE",
          "participantInfos": {
            "$all": [
              { "$elemMatch": { "idAccount": ?0 } },
              { "$elemMatch": { "idAccount": ?1 } }
            ]
          },
          "numberMember": 2
        }
    """)
    Optional<Conversation> findDoubleConversationByParticipants(String id1, String id2);

}
