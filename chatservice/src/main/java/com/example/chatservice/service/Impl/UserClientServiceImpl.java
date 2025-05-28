package com.example.chatservice.service.Impl;

import com.example.chatservice.service.UserClientService;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.commonservice.model.UserServiceModal.AccountBasicResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserClientServiceImpl {
    private final UserClientService userClientService;

    public UserClientServiceImpl(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    // Gọi và cache
    @Cacheable(value = "usersByIds", key = "#root.args[0]")
    public Map<Integer, AccountBasicResponse> getUsersByIds(List<Integer> ids) {
        System.out.println("Call in cache");
        ResponseEntity<Map<Integer, AccountBasicResponse>> response = userClientService.getUsersByIds(ids);
        return response.getBody();
    }

    // Có thể thêm hàm này nếu cần
    public ResponseDataMessage getBasicUserById(int id) {
        return userClientService.getBasicUserById(id).getBody();
    }
}
