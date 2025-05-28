package com.example.chatservice.service;

import com.example.chatservice.entity.Message;
import com.example.commonservice.model.PageResponse;

import java.util.List;

public interface MessageService {
     List<Message> getMessagesByConversation(String idConversation);
     PageResponse<Message> getMessagesByIdConversation(String idConversation, int page, int size, String sortDirection);
     Message saveMessage(Message message);
     Message updateMessage(Message message, String idMessage);
     void deleteByIdConversation(String idConversation);

}
