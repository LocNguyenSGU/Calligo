package com.example.userservice.service;

import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
public interface UserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);
    List<UserResponse> getAllUser();

    boolean existsByPhoneNumber(String phone);
    boolean existsByIdAccount(int idAccount);
}
