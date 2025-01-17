package com.example.userservice.mapper;


import com.example.userservice.dto.request.PermissionCreateRequest;
import com.example.userservice.entity.Permission;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreateRequest permissionCreateRequest);
}
