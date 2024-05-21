package com.microservice.user.services.interfaces;

import com.microservice.user.http.response.ResponseApi;
import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    ResponseApi<List<UserResponseDTO>> getAllByTeamId(Long teamId);
    ResponseApi<UserResponseDTO> getByCredentials(LoginDTO loginDTO);
    ResponseApi<?> updateStatus(Boolean status, Long id);
    ResponseApi<?> updateAllUsersSubscriptionStatus(Boolean status, Long teamId);

}
