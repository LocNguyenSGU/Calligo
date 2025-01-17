package com.example.userservice.service;


import com.example.userservice.dto.request.RoleCreateRequest;
import com.example.userservice.entity.Role;

public interface RoleService {
    void createRole(RoleCreateRequest request);
    void updateRole(RoleCreateRequest request, int idRole);
    void deleteRole(int idRole);

    Role getRoleById(int idRole);

}
