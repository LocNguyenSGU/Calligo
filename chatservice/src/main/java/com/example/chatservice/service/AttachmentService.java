package com.example.chatservice.service;

import com.example.chatservice.entity.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentService {
    Attachment createAttachment(Attachment attachment);
    Attachment getAttachmentById(String id);
    List<Attachment> getAttachmentsByMessageId(String idMessage);
    List<Attachment> getAllAttachments();
    Attachment updateAttachment(String id, Attachment attachment);
    void deleteAttachment(String id);
    void deleteByIdConversation(String idConversation);
}
