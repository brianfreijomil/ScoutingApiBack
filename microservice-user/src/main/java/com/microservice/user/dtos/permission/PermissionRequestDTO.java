package com.microservice.user.dtos.permission;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequestDTO {

    @NotNull(message = "permission cannot be null")
    @NotEmpty(message = "permission cannot be empty")
    private String name;
}
