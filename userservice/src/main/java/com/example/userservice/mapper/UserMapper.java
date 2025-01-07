package com.example.userservice.mapper;

import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreateRequest toUserCreateRequest(User user);
    User toUser(UserCreateRequest userCreateRequest);
    UserResponse toUserResponse(User user);
}
