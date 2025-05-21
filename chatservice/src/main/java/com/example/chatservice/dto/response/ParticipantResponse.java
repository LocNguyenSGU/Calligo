package com.example.chatservice.dto.response;

import com.example.chatservice.eenum.ParicipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "participants")
public class ParticipantResponse {
    private String idConversation;
    private String idAccount;
    private ParicipantRole role;
    private LocalDateTime dateJoin;
    private boolean isTyping;
}
