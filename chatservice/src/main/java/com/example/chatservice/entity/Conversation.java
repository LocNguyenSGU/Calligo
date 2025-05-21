package com.example.chatservice.entity;

import com.example.chatservice.eenum.ConversationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    @Id
    private String idConversation;
    private ConversationType type;
    private String name;
    private String avatar;
    private LocalDateTime dateCreate;
    private String lastMessageContent;
    private int numberMember;
    private List<ParticipantInfo> participantInfos;
}
