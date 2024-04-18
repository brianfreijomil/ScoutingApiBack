package com.microservice.gateway.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequestDTO {

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    private String password;

}
