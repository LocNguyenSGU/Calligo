package com.example.permissionservice.repository;

import com.example.permissionservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleNameAndIdRoleNot(String roleName, int idRole);
}
