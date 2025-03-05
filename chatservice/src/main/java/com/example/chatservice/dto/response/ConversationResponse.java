package com.example.chatservice.dto.response;

import com.example.chatservice.eenum.ConversationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    @Id
    private String idConversation;
    private ConversationType type;  // "double" hoặc "group"
    private String name;
    private String avatar;
    private LocalDateTime dateCreate;
    private String idLastMessage;
    private int numberMember;
}
