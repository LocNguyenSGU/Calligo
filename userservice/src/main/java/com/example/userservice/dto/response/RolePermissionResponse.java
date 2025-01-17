package com.example.userservice.dto.response;

import com.example.userservice.eenum.RolePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponse {
    private int idRole;
    private int idPermission;
    private RolePermissionEnum action;
}
