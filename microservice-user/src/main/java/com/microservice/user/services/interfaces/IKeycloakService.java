package com.microservice.user.services.interfaces;

import com.microservice.user.http.response.ResponseApi;
import com.microservice.user.model.dtos.user.SessionDTO;
import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IKeycloakService {

    ResponseApi<SessionDTO> startSession(LoginDTO loginUser);
    ResponseApi<List<UserResponseDTO>> findAllUsers(String idCurrentUser);
    ResponseApi<List<UserResponseDTO>> findAllUsersByTeamId(Long teamId);
    ResponseApi<UserResponseDTO> searchUserByUsername(String username);
    ResponseApi<?> createUser(UserRequestDTO user);
    ResponseApi<?> deleteUser(String userId);
    ResponseApi<?> updateUser(String userId, UserRequestDTO user);

}
