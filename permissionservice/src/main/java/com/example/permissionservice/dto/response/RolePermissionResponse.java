package com.example.permissionservice.dto.response;

import com.example.permissionservice.eenum.RolePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponse {
    private int idRole;
    private int idPermission;
    private RolePermissionEnum action;
}
