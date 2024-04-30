package com.microservice.user.services.interfaces;

import com.microservice.user.model.dtos.user.request.UserRequestKCDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IKeycloakService {

    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    ResponseEntity<?> createUser(UserRequestKCDTO user);
    ResponseEntity<?> deleteUser(String userId);
    ResponseEntity<?> updateUser(String userId, UserRequestKCDTO user);

}
