package com.example.chatservice.entity;

import com.example.chatservice.eenum.ParicipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "participants")
public class Participant {
    @Id
    private String idParticipant;
    private String idConversation;
    private String idAccount;
    private ParicipantRole role;
    private LocalDateTime dataJoin;
}
