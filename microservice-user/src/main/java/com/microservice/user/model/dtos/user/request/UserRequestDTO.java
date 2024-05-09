package com.microservice.user.model.dtos.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "ID cannot be null")
    @NotEmpty(message = "ID cannot be empty")
    private Long id;
    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @NotNull(message = "surname cannot be null")
    @NotEmpty(message = "surname cannot be empty")
    private String surname;
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    private String password;
    private Set<String> roles;
    @NotNull(message = "teamId cannot be null")
    private Long teamId;


}
