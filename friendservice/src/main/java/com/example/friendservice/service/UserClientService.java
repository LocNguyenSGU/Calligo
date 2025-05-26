package com.example.friendservice.service;

import com.example.commonservice.model.ResponseDataMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice")
public interface UserClientService {
    @GetMapping("/api/v1/user-service/accounts/basic/{id}")
    ResponseEntity<ResponseDataMessage> getBasicUserById(@PathVariable("id") int id);
}
