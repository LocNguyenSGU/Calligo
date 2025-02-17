package com.example.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    private String idAttachment;
    private String idMessage;
    private String idConversation;
    private String type;
    private String url;
    private String size;
    private LocalDateTime timeUpload;
    private MetaData metaData;

    public static class MetaData {
        private int width;
        private int height;
    }
}
