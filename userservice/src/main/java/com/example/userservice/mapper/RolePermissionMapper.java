package com.example.userservice.mapper;

import com.example.userservice.dto.request.RolePermissionRequest;
import com.example.userservice.entity.RolePermisson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {
    RolePermisson toRolePermission(RolePermissionRequest rolePermissioRequest);
}
