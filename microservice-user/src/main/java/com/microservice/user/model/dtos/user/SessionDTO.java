package com.microservice.user.model.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private String username;
    private String token;
    private String refreshToken;

}
