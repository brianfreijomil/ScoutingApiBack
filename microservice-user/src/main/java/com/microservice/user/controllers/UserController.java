package com.microservice.user.controllers;

import com.microservice.user.http.response.ResponseApi;
import com.microservice.user.model.dtos.user.SessionDTO;
import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import com.microservice.user.services.interfaces.IKeycloakService;
import com.microservice.user.services.interfaces.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private IKeycloakService keycloakService;

    @PostMapping("/log-in")
    public ResponseApi<SessionDTO> startSession(@RequestBody @Valid LoginDTO loginDTO) {
        return this.keycloakService.startSession(loginDTO);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE')")
    public ResponseApi<List<UserResponseDTO>> getAllUsers(@Valid @NotNull @NotEmpty String id_current_user) {
        return this.keycloakService.findAllUsers(id_current_user);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseApi<UserResponseDTO> getUserByUsername(@PathVariable @NotNull @NotEmpty String username) {
        return this.keycloakService.searchUserByUsername(username);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseApi<?> createUser(@RequestBody @Valid UserRequestDTO user) {
        return this.keycloakService.createUser(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseApi<?> updateUser(@RequestBody @Valid UserRequestDTO user, @PathVariable String userId) {
        return this.keycloakService.updateUser(userId,user);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseApi<?> deleteUser(@PathVariable String userId) {
        return this.keycloakService.deleteUser(userId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseApi<List<UserResponseDTO>> getAllUsersByTeamId(@NotNull Long team_id) {
        log.info("team ID: " + team_id.toString());
        return this.keycloakService.findAllUsersByTeamId(team_id);
    }

    /*
    * ALL METHODS MUST EXIST
    *
    *

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
