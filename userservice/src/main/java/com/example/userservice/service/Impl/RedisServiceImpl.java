package com.example.userservice.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements com.example.userservice.service.RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void setOnline(int idAccount, String deviceId) {
        String key = "user:" + idAccount + ":deviceId:";
        redisTemplate.opsForSet().add(key, deviceId);
    }

    @Override
    public void removeOnline(int idAccount, String deviceId) {
        String key = "user:" + idAccount + ":deviceId:";
        redisTemplate.opsForSet().remove(key, deviceId);
    }

    @Override
    public boolean isOnline(int idAccount) {
        String key = "user:" + idAccount + ":deviceId:";
        Long count = redisTemplate.opsForSet().size(key);
        return count != null && count > 0;
    }

    @Override
    public void setLastSeen(int userId, String timestamp) {
        String key = "lastSeen:" + userId;
        redisTemplate.opsForValue().set(key, timestamp);
    }

    @Override
    public String getLastSeen(int userId) {
        String key = "lastSeen:" + userId;
        return redisTemplate.opsForValue().get(key).toString();
    }

    @Override
    public void removeLastSeen(int userId) {
        String key = "lastSeen:" + userId;
        redisTemplate.delete(key);
    }
}
