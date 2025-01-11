package com.example.permissionservice.controller;

import com.example.commonservice.model.ErrorMessage;
import com.example.commonservice.model.OKMessage;
import com.example.permissionservice.dto.request.RoleCreateRequest;
import com.example.permissionservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        roleService.createRole(roleCreateRequest);
        return new ResponseEntity<>(new OKMessage("Tao role thanh cong"), HttpStatus.OK);
    }

}
