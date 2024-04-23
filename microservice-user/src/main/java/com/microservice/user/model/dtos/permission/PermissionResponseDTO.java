package com.microservice.user.model.dtos.permission;

import com.microservice.user.model.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponseDTO {

    private Long id;
    private String name;

    public PermissionResponseDTO(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
    }
}
