package com.microservice.user.services.interfaces;

import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    ResponseEntity<List<UserResponseDTO>> getAllByTeamId(Long teamId);
    ResponseEntity<UserResponseDTO> getByCredentials(LoginDTO loginDTO);
    ResponseEntity<?> updateStatus(Boolean status, Long id);
    ResponseEntity<?> updateAllUsersSubscriptionStatus(Boolean status, Long teamId);

}
