package com.example.userservice.repository;

import com.example.userservice.entity.RolePermisson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermisson, Integer> {
    void deleteAllByRole_IdRole(int idRole);
}
