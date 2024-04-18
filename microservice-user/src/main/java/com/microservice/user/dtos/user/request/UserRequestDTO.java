package com.microservice.user.dtos.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;
    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @NotNull(message = "surname cannot be null")
    @NotEmpty(message = "surname cannot be empty")
    private String surname;
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @NotNull(message = "roleDescription cannot be null")
    @NotEmpty(message = "roleDescription cannot be empty")
    private String roleDescription;
    @NotNull(message = "status cannot be null")
    private Boolean status;
    @NotNull(message = "teamId cannot be null")
    private Long teamId;

}
