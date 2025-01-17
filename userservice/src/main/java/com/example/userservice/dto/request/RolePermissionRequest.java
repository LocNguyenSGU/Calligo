package com.example.userservice.dto.request;

import com.example.commonservice.customEnum.ValidEnum;
import com.example.userservice.eenum.RolePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionRequest {
    private int idPermission;
    @ValidEnum(enumClass = RolePermissionEnum.class, message = "Invalid action value")
    private String action;
}
