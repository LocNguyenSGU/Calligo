package com.example.chatservice.service;

public interface RedisService {
    void setOnline(int idAccount, String deviceId);

    void removeOnline(int idAccount, String deviceId);

    boolean isOnline(String idAccount);

    void setLastSeen(String userId, String timestamp);

    String getLastSeen(String userId);

    void removeLastSeen(int userId);
}
