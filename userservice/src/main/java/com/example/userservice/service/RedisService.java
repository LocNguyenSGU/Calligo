package com.example.userservice.service;

public interface RedisService {
    void setOnline(int idAccount, String deviceId);

    void removeOnline(int idAccount, String deviceId);

    boolean isOnline(int idAccount);

    void setLastSeen(int userId, String timestamp);

    String getLastSeen(int userId);

    void removeLastSeen(int userId);
}
