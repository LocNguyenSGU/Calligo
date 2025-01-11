package com.example.permissionservice.mapper;

import com.example.permissionservice.dto.request.RoleCreateRequest;
import com.example.permissionservice.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreateRequest roleCreateRequest);
}
