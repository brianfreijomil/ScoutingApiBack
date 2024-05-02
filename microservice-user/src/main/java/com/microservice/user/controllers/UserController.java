package com.microservice.user.controllers;

import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import com.microservice.user.services.interfaces.IKeycloakService;
import com.microservice.user.services.interfaces.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IKeycloakService keycloakService;

    @GetMapping("")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE')")
    public List<UserResponseDTO> getAllUsers() {
        return this.keycloakService.findAllUsers();
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public UserResponseDTO getUserByUsername(@PathVariable @NotNull @NotEmpty String username) {
        return this.keycloakService.searchUserByUsername(username);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO user) {
        return this.keycloakService.createUser(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequestDTO user, @PathVariable String userId) {
        return this.keycloakService.updateUser(userId,user);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        return this.keycloakService.deleteUser(userId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public List<UserResponseDTO> getAllUsersByTeamId(@NotNull Long teamId) {
        return this.keycloakService.findAllUsersByTeamId(teamId);
    }

    /*
    * ALL METHODS MUST EXIST
    *
    *

    @PostMapping("/log-in")
    public UserResponseDTO startSession(@RequestBody @Valid LoginDTO loginDTO) {
        return this.userService.getByCredentials(loginDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserStatus(Boolean status, @PathVariable Long id) {
        return this.userService.updateStatus(status,id);
    }

    @PatchMapping("/subscription/{teamId}")
    public ResponseEntity<?> updateAllUsersSubscriptionStatus(Boolean status, @PathVariable Long teamId) {
        return this.userService.updateAllUsersSubscriptionStatus(status,teamId);
    }
    *
    * */

}
