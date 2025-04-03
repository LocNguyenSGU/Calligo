package com.example.chatservice.service.Impl;

import com.example.chatservice.entity.Attachment;
import com.example.chatservice.repository.AttachmentRepository;
import com.example.chatservice.service.AttachmentService;
import com.example.commonservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment createAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment getAttachmentById(String id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("attachment not found: " + id));
    }

    @Override
    public List<Attachment> getAttachmentsByMessageId(String idMessage) {
        return attachmentRepository.findByIdMessage(idMessage);
    }

    @Override
    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    @Override
    public Attachment updateAttachment(String id, Attachment attachment) {
        if (attachmentRepository.existsById(id)) {
            attachment.setIdAttachment(id);
            return attachmentRepository.save(attachment);
        }
        throw new ResourceNotFoundException("attachment not found: " + id);
    }

    @Override
    public void deleteAttachment(String id) {
        attachmentRepository.deleteById(id);
    }
}
