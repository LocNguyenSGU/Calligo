package com.example.permissionservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateRequest {
    @NotNull(message = "Role name khong duoc bo trong")
    @Size(min = 1, max = 60, message = "Role name tu 1 -> 60 ki tu")
    private String roleName;

    private String description;

    @Valid // Quan trọng: cần thêm @Valid để kiểm tra các phần tử bên trong
    private List<RolePermissionRequest> rolePermissionRequests;
}
