package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;

    @Column(nullable = false, length = 60, unique = true)
    private String roleName;

    @Column(nullable = false, length = 150)
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<RolePermisson> rolePermissonList;

    @OneToMany(mappedBy = "role")
    private List<Account> accountList;

}
