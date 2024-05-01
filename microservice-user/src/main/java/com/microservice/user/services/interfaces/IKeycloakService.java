package com.microservice.user.services.interfaces;

import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IKeycloakService {

    List<UserResponseDTO> findAllUsers();
    UserResponseDTO searchUserByUsername(String username);
    ResponseEntity<?> createUser(UserRequestDTO user);
    ResponseEntity<?> deleteUser(String userId);
    ResponseEntity<?> updateUser(String userId, UserRequestDTO user);

}
