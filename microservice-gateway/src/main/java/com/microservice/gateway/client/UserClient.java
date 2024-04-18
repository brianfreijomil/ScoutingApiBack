package com.microservice.gateway.client;

import com.microservice.gateway.dtos.AuthLoginRequestDTO;
import com.microservice.gateway.dtos.UserDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-user", url = "localhost:8092/api/users")
public interface UserClient {

    @PostMapping("/login")
    public UserDTO startSession(@RequestBody @Valid AuthLoginRequestDTO authLoginRequestDTO);

}
