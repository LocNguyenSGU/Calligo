package com.example.permissionservice.service.impl;

import com.example.commonservice.exception.InvalidInputException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.permissionservice.dto.request.RoleCreateRequest;
import com.example.permissionservice.entity.Permission;
import com.example.permissionservice.entity.Role;
import com.example.permissionservice.entity.RolePermisson;
import com.example.permissionservice.mapper.RoleMapper;
import com.example.permissionservice.mapper.RolePermissionMapper;
import com.example.permissionservice.repository.RoleRepository;
import com.example.permissionservice.service.PermissionService;
import com.example.permissionservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void createRole(RoleCreateRequest roleCreateRequest) {
        try {
            // 1. Tạo danh sách RolePermission từ request
            List<RolePermisson> rolePermissonList = roleCreateRequest
                    .getRolePermissionCreateRequests()
                    .stream()
                    .map(rolePermissionRequest -> {
                        RolePermisson rolePermisson = rolePermissionMapper.toRolePermission(rolePermissionRequest);

                        // 2. Lấy Permission từ PermissionService
                        int idPermission = rolePermissionRequest.getIdPermission();
                        Permission permission = permissionService.getPermissionById(idPermission);

                        // 3. Set Permission vào RolePermission
                        rolePermisson.setPermission(permission);

                        return rolePermisson;
                    })
                    .collect(Collectors.toList());

            // 4. Tạo Role từ request
            Role role = roleMapper.toRole(roleCreateRequest);

            // 5. Set Role vào từng RolePermission
            rolePermissonList.forEach(rolePermisson -> rolePermisson.setRole(role));

            // 6. Gắn danh sách RolePermission vào Role
            role.setRolePermissonList(rolePermissonList);

            // 7. Lưu Role (với cascade thì RolePermission cũng sẽ được lưu)
            roleRepository.save(role);
        }catch (DataIntegrityViolationException ex) {
            throw new InvalidInputException("Ten quyen da ton tai: " + roleCreateRequest.getRoleName());
        }
    }
}
