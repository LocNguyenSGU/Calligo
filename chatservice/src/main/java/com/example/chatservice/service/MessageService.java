package com.example.chatservice.service;

import com.example.chatservice.entity.Message;

import java.util.List;

public interface MessageService {
     List<Message> getMessagesByConversation(String idConversation);
     Message saveMessage(Message message);

     Message updateMessage(Message message, String idMessage);

}
