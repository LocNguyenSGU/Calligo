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
import java.util.Map;

@Document(collection = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private String idMessage;
    private String idConversation;
    private String idAccountSent;
    private MessageEnum status;
    private String content;
    private MessageType type;
    private LocalDateTime timeSent;
    private LocalDateTime timeUpdate;
    private List<Attachment> attachments;
    private Map<String, String> reactions;
    private String replyToMessageId;
    private boolean isEdited;
    private List<String> editHistory;
}
