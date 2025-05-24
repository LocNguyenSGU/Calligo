package com.example.chatservice.repository;

import com.example.chatservice.entity.Attachment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends MongoRepository<Attachment, String> {
    List<Attachment> findByIdMessage(String idMessage);
    Optional<Attachment> findById(String idAttachment);
    void deleteByIdConversation(String idConversation);
}
