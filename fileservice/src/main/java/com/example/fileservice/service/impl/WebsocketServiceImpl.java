package com.example.fileservice.service.impl;

import com.example.fileservice.dto.UploadCompleteMessage;
import com.example.fileservice.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketServiceImpl implements WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendUploadProgress(String sessionId, double progress) {
        simpMessagingTemplate.convertAndSend("/topic/upload-progress/" + sessionId, progress);
    }

    @Override
    public void sendUploadComplete(String sessionId, String url, String messageId) {
        simpMessagingTemplate.convertAndSend("/topic/upload-complete/" + sessionId, new UploadCompleteMessage(messageId, url));
    }
}
