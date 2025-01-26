package com.example.userservice.entity;

import com.example.userservice.eenum.RolePermissionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RolePermisson {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int idRolePermisson;

   @ManyToOne
   @JoinColumn(name = "idRole")
   private Role role;

   @ManyToOne
   @JoinColumn(name="idPermission")
   private Permission permission;

    @Enumerated(EnumType.STRING)
    private RolePermissionEnum action;
}
