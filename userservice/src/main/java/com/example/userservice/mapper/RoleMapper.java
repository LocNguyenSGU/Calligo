package com.example.userservice.mapper;


import com.example.userservice.dto.request.RoleCreateRequest;
import com.example.userservice.dto.response.RoleResponse;
import com.example.userservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreateRequest roleCreateRequest);
    @Mapping(source = "rolePermissonList", target = "rolePermissionList", ignore = true)
    RoleResponse toRoleResponse(Role role);
    default RoleResponse mapWithLog(Role role) {
        System.out.println("Mapping Role: " + role);
        return toRoleResponse(role);
    }
}
