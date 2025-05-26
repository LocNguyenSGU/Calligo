package com.example.chatservice.service;

import com.example.chatservice.dto.response.ResponseData;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.commonservice.model.UserServiceModal.AccountBasicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "userservice")
public interface UserClientService {
    @GetMapping("/api/v1/user-service/accounts/basic/{id}")
    ResponseEntity<ResponseDataMessage> getBasicUserById(@PathVariable("id") int id);

    @GetMapping("/api/v1/user-service/accounts/basic/internal/users")
    ResponseEntity<Map<Integer, AccountBasicResponse>> getUsersByIds(@RequestParam("ids") List<Integer> ids);
}
