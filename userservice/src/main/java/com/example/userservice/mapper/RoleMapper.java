package com.example.userservice.mapper;


import com.example.userservice.dto.request.RoleCreateRequest;
import com.example.userservice.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreateRequest roleCreateRequest);
}
