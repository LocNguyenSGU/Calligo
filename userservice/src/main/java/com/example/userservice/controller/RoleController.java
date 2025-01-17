package com.example.userservice.controller;

import com.example.commonservice.model.OKMessage;
import com.example.userservice.dto.request.RoleCreateRequest;
import com.example.userservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        roleService.createRole(roleCreateRequest);
        return new ResponseEntity<>(new OKMessage("Tạo role thanh cong"), HttpStatus.OK);
    }
    @PutMapping("/{idRole}")
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleCreateRequest roleCreateRequest, @PathVariable int idRole) {
        roleService.updateRole(roleCreateRequest, idRole);
        return new ResponseEntity<>(new OKMessage("Sửa role thanh cong"), HttpStatus.OK);
    }
    @DeleteMapping("/{idRole}")
    public ResponseEntity<?> deleteRole(@PathVariable int idRole) {
        roleService.deleteRole(idRole);
        return new ResponseEntity<>(new OKMessage("Xoá role thanh cong"), HttpStatus.OK);
    }

}
