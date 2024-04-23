package com.microservice.user.model.entities;

import com.microservice.user.model.dtos.permission.PermissionRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name; //READ, CREATE, UPDATE, DELETE, REFACTOR...

    public Permission(PermissionRequestDTO permissionRequestDTO) {
        this.name = permissionRequestDTO.getName();
    }

}
