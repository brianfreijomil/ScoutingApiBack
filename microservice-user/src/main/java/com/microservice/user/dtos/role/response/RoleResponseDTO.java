package com.microservice.user.dtos.role.response;

import com.microservice.user.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {

    private Long id;
    private String type;

    public RoleResponseDTO(Role role) {
        this.id = role.getId();
        this.type = role.getRoleName();
    }
}
