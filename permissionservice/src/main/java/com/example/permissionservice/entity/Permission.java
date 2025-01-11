package com.example.permissionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermission;

    @Column(nullable = false, length = 60)
    private String permissionName;

    @OneToMany(mappedBy = "permission")
    private List<RolePermisson> rolePermissonList;

}
