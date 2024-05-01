package com.microservice.user.model.dtos.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String id;
    private String username;
    private String lastname;
    private String firstname;
    private List<String> roles;
    private Boolean enable;
    private Long teamId;

}
