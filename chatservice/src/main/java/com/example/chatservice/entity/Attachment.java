package com.example.chatservice.entity;

import com.example.chatservice.eenum.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachemnts")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConversation;
    @ManyToOne
    @JoinColumn(name = "idMesage")
    private Message message;
    private MessageType messageType;
    private String url;
}
