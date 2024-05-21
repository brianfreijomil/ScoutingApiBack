package com.microservice.user.model.dtos.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String id;
    private String username;
    private String email;
    private String lastName;
    private String firstName;
    //private List<String> roles;
    private Boolean enabled;
    private String teamId;

}
