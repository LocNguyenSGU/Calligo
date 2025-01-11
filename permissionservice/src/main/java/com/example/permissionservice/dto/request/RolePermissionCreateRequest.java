package com.example.permissionservice.dto.request;

import com.example.commonservice.customEnum.ValidEnum;
import com.example.permissionservice.eenum.RolePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionCreateRequest {
    private int idPermission;
    @ValidEnum(enumClass = RolePermissionEnum.class, message = "Invalid action value")
    private String action;
}
