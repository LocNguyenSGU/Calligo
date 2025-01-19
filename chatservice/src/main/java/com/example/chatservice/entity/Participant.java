package com.example.chatservice.entity;

import com.example.chatservice.eenum.ParicipantRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipant;

    @ManyToOne
    @JoinColumn(name = "idConversation")
    private Conversation conversation;

    private int idAccount;
    private ParicipantRole paricipantRole;

    private LocalDateTime dateCreate;
}
