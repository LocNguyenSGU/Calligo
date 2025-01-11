package com.example.permissionservice.mapper;

import com.example.permissionservice.dto.request.PermissionCreateRequest;
import com.example.permissionservice.entity.Permission;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreateRequest permissionCreateRequest);
}
