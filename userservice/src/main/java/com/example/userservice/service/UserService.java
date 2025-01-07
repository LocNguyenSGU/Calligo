package com.example.userservice.service;

import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
public interface UserService {
    UserCreateRequest createUser(UserCreateRequest userCreateRequest);
    List<UserResponse> getAllUser();
}
