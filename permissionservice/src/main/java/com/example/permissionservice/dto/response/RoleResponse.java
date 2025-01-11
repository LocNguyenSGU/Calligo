package com.example.permissionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private int idRole;
    private String roleName;
    private List<RolePermissionResponse> rolePermissionResponseList;
}
