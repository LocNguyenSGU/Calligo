package com.example.permissionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST)
    private List<RolePermisson> rolePermissonList;

}
