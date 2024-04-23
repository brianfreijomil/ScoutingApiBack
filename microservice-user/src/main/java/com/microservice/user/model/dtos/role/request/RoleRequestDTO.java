package com.microservice.user.model.dtos.role.request;

import com.microservice.user.model.entities.Permission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {

    private Long id;

    @NotNull(message = "roleName cannot be null")
    @NotEmpty(message = "roleName cannot be empty")
    private String roleName;

    private List<Permission> permissions;

}
