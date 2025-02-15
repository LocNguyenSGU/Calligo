package com.example.userservice.service.Impl;

import com.example.userservice.repository.RolePermissionRepository;
import com.example.userservice.service.RolePermissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl implements RolePermissonService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Override
    public void deleteAllRolePermissionByIdRole(int idRole) {
        rolePermissionRepository.deleteAllByRole_IdRole(idRole);
    }
}
