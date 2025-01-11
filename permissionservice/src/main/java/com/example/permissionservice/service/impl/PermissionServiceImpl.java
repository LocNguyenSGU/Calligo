package com.example.permissionservice.service.impl;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.permissionservice.entity.Permission;
import com.example.permissionservice.repository.PermissionRepository;
import com.example.permissionservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Permission getPermissionById(int idPermission) {
        return permissionRepository.findById(idPermission).orElseThrow(()-> new ResourceNotFoundException("Khong co perrmission voi id: " + idPermission));
    }
}
