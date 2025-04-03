package com.example.chatservice.controller;

import com.example.chatservice.entity.Attachment;
import com.example.chatservice.service.AttachmentService;
import com.example.commonservice.model.ResponseDataMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-service/attachments")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    // Tạo mới Attachment
    @PostMapping
    public ResponseEntity<ResponseDataMessage> createAttachment(@RequestBody Attachment attachment) {
        Attachment createdAttachment = attachmentService.createAttachment(attachment);
        return new ResponseEntity<>(new ResponseDataMessage("Create attachments successfully", createdAttachment), HttpStatus.CREATED);
    }

    // Lấy Attachment theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataMessage> getAttachmentById(@PathVariable String id) {
        Attachment attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(new ResponseDataMessage("Attachment retrieved successfully", attachment));
    }

    // Lấy tất cả Attachments
    @GetMapping
    public ResponseEntity<ResponseDataMessage> getAllAttachments() {
        List<Attachment> attachments = attachmentService.getAllAttachments();
        return ResponseEntity.ok(new ResponseDataMessage("Attachments retrieved successfully", attachments));
    }

    // Lấy tất cả Attachments theo ID của Message
    @GetMapping("/message/{idMessage}")
    public ResponseEntity<ResponseDataMessage> getAttachmentsByMessageId(@PathVariable String idMessage) {
        List<Attachment> attachments = attachmentService.getAttachmentsByMessageId(idMessage);
        return ResponseEntity.ok(new ResponseDataMessage("Attachments retrieved successfully", attachments));
    }

    // Cập nhật Attachment
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataMessage> updateAttachment(@PathVariable String id, @RequestBody Attachment attachment) {
        Attachment updatedAttachment = attachmentService.updateAttachment(id, attachment);
        return ResponseEntity.ok(new ResponseDataMessage("Attachment updated successfully", updatedAttachment));
    }

    // Xóa Attachment
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataMessage> deleteAttachment(@PathVariable String id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.ok(new ResponseDataMessage("Attachment deleted successfully", null));
    }
}