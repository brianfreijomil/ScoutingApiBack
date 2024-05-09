package com.microservice.user.model.dtos.team.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDTO {

    @NotNull(message = "id cannot be null")
    @NotEmpty(message = "id cannot be empty")
    private Long id;

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    private String email;

    @NotNull(message = "contactNumber cannot be null")
    @NotEmpty(message = "contactNumber cannot be empty")
    private String contactNumber;

    @NotNull(message = "subscribed cannot be null")
    private Boolean subscribed;

    @NotNull(message = "dateOfExpired cannot be null")
    private Date dateOfExpired;
}
