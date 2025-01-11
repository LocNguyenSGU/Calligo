package com.example.permissionservice.mapper;

import com.example.permissionservice.dto.request.RolePermissionRequest;
import com.example.permissionservice.entity.RolePermisson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {
    RolePermisson toRolePermission(RolePermissionRequest rolePermissioRequest);
}
