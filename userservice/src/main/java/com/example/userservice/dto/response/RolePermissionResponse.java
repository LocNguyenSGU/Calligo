package com.example.userservice.dto.response;

import com.example.userservice.eenum.RolePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponse {
    private int idPermission;
    private String permissionName;
    private RolePermissionEnum action;
}
