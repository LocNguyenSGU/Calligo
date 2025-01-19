package com.example.chatservice.entity;

import com.example.chatservice.eenum.ConversationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConversation;
    private ConversationType conversationType;
    private String name;
    private String inAccountCreate;
    private LocalDateTime dateCreate;
    private int idLastMessage;
    @OneToMany(mappedBy = "conversation")
    private List<Message> messageList;
    @OneToMany(mappedBy = "conversation")
    private List<Participant> participantList;

}
