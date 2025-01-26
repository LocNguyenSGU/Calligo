package com.example.userservice.mapper;

import com.example.userservice.dto.request.RolePermissionRequest;
import com.example.userservice.dto.response.RolePermissionResponse;
import com.example.userservice.entity.RolePermisson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {
    RolePermisson toRolePermission(RolePermissionRequest rolePermissioRequest);
    @Mapping(source = "permission.idPermission", target = "idPermission")
    @Mapping(source = "permission.permissionName", target = "permissionName")
    RolePermissionResponse toRolePermissonResponse(RolePermisson rolePermisson);
}
