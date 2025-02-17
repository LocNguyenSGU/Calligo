package com.example.chatservice.service;

import com.example.chatservice.entity.Message;

import java.util.List;

public interface MessageService {
    public List<Message> getMessagesByConversation(String idConversation);

    public Message saveMessage(Message message);
}
