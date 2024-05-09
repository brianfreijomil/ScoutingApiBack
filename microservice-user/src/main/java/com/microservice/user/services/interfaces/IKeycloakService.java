package com.microservice.user.services.interfaces;

import com.microservice.user.model.dtos.user.SessionDTO;
import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IKeycloakService {

    ResponseEntity<SessionDTO> startSession(LoginDTO loginUser);
    ResponseEntity<List<UserResponseDTO>> findAllUsers();
    ResponseEntity<List<UserResponseDTO>> findAllUsersByTeamId(Long teamId);
    ResponseEntity<UserResponseDTO> searchUserByUsername(String username);
    ResponseEntity<?> createUser(UserRequestDTO user);
    ResponseEntity<?> deleteUser(String userId);
    ResponseEntity<?> updateUser(String userId, UserRequestDTO user);

}
