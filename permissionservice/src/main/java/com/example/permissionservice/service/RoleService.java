package com.example.permissionservice.service;

import com.example.permissionservice.dto.request.RoleCreateRequest;


public interface RoleService {
    void createRole(RoleCreateRequest request);
}
