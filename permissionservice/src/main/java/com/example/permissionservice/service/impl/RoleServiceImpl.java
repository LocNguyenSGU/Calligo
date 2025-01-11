package com.example.permissionservice.service.impl;

import com.example.commonservice.exception.InvalidInputException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.permissionservice.dto.request.RoleCreateRequest;
import com.example.permissionservice.dto.request.RolePermissionRequest;
import com.example.permissionservice.entity.Permission;
import com.example.permissionservice.entity.Role;
import com.example.permissionservice.entity.RolePermisson;
import com.example.permissionservice.mapper.RoleMapper;
import com.example.permissionservice.mapper.RolePermissionMapper;
import com.example.permissionservice.repository.RoleRepository;
import com.example.permissionservice.service.PermissionService;
import com.example.permissionservice.service.RolePermissonService;
import com.example.permissionservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RolePermissonService rolePermissonService;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void createRole(RoleCreateRequest roleCreateRequest) {
        try {
            // 1. Tạo danh sách RolePermission từ request
            List<RolePermisson> rolePermissonList = roleCreateRequest
                    .getRolePermissionRequests()
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

    @Override
    @Transactional // Đảm bảo các thay đổi được thực hiện trong một transaction
    public void updateRole(RoleCreateRequest request, int idRole) {
        // Lấy Role từ DB
        Role role = roleRepository.findById(idRole)
                .orElseThrow(() -> new ResourceNotFoundException("Không có role với id: " + idRole));

        // Kiểm tra tên Role có tồn tại hay không
        if (roleRepository.existsByRoleNameAndIdRoleNot(request.getRoleName(), idRole)) {
            throw new InvalidInputException("Tên quyền này đã tồn tại: " + request.getRoleName());
        }

        // Cập nhật các trường cơ bản
        role.setDescription(request.getDescription());
        role.setRoleName(request.getRoleName());
        rolePermissonService.deleteAllRolePermissionByIdRole(idRole);

        // Tạo danh sách RolePermission mới
        List<RolePermisson> newList = request.getRolePermissionRequests()
                .stream()
                .map(rolePermissionRequest -> {
                    RolePermisson rolePermisson = rolePermissionMapper.toRolePermission(rolePermissionRequest);
                    Permission permission = permissionService.getPermissionById(rolePermissionRequest.getIdPermission());
                    rolePermisson.setPermission(permission);
                    rolePermisson.setRole(role);
                    return rolePermisson;
                })
                .collect(Collectors.toList());

        // Set lại danh sách mới
        role.setRolePermissonList(newList);

        // Lưu Role, Hibernate sẽ tự động xóa các orphan records và cập nhật danh sách mới
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(int idRole) {
        roleRepository.findById(idRole)
                .orElseThrow(() -> new ResourceNotFoundException("Không có role với id: " + idRole));
        roleRepository.deleteById(idRole);
    }
}