package com.example.chatservice.entity;

import com.example.chatservice.eenum.MessageEnum;
import com.example.chatservice.eenum.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document(collection = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private String idMessage;
    private String idConversation;
    private String idAccountSent;
    private List<String> idAccountReceives;
    private MessageEnum status;
    private String content;
    private MessageType type;
    private LocalDateTime timeSent;
    private List<Attachment> attachments;
}
