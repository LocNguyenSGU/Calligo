package com.example.chatservice.observe;

import com.example.chatservice.entity.Message;

public interface MessageObserve {
    void onMessageReceived(Message message);
    String getSocketSessionId();
}
