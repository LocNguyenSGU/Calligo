package com.example.fileservice.service;

public interface WebSocketService {
    // Method to send progress updates
    public void sendUploadProgress(String sessionId, double progress);
    // Method to send completed URL and messageId to FE
    public void sendUploadComplete(String sessionId, String url, String messageId);
}
