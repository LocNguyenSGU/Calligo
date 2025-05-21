package com.example.chatservice.entity;

import com.example.chatservice.eenum.ParicipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "participantInfos")
public class ParticipantInfo {
    private String idAccount;
    private ParicipantRole role;
    private String nickname;
    private LocalDateTime dateJoin;
}