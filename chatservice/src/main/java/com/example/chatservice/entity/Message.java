package com.example.chatservice.entity;

import com.example.chatservice.eenum.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Element;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMessage;
    private int idAccountSent;
    private int idAccountRecive;
    @ManyToOne
    @JoinColumn(name = "idConversation")
    private Conversation conversation;
    private String content;
    private MessageType messageType;
    private String dateCreate;
    @OneToMany(mappedBy = "message")
    private List<Attachment> attachmentList;

}
