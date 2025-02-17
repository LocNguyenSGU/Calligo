package com.example.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "message_read_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadStatus {
    @Id
    private String idMessageReadStatus;
    private String idConversation;
    private String idAccount;
    private String idLastMessageSeen;
    private LocalDateTime timeSeen;
}
