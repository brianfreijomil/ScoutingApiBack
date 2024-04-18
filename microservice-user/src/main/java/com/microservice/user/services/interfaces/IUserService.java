package com.microservice.user.services.interfaces;

import com.microservice.user.dtos.user.request.LoginDTO;
import com.microservice.user.dtos.user.request.UserRequestDTO;
import com.microservice.user.dtos.user.response.UserResponseDTO;
import com.microservice.user.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    List<UserResponseDTO> getAll();

    List<UserResponseDTO> getAllByTeamId(Long teamId);

    UserResponseDTO getById(Long id);

    UserResponseDTO getByCredentials(LoginDTO loginDTO);

    ResponseEntity<?> create(UserRequestDTO user);

    ResponseEntity<?> update(UserRequestDTO user, Long id);

    ResponseEntity<?> updateStatus(Boolean status, Long id);

    ResponseEntity<?> updateAllUsersSubscriptionStatus(Boolean status, Long teamId);

    ResponseEntity<?> delete(Long id);

}
