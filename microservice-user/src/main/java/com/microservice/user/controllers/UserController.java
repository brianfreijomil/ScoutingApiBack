package com.microservice.user.controllers;

import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.request.UserRequestKCDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import com.microservice.user.services.interfaces.IKeycloakService;
import com.microservice.user.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IKeycloakService keycloakService;

    @GetMapping("/keycloak/users")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public List<UserRepresentation> getUsersKeycloak() {
        return this.keycloakService.findAllUsers();
    }

    @GetMapping("/keycloak/users/{username}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public List<UserRepresentation> getUserByUsernameKeycloak(@PathVariable String username) {
        return this.keycloakService.searchUserByUsername(username);
    }

    @PostMapping("/keycloak/users/create")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseEntity<?> createUserKeycloak(@RequestBody @Valid UserRequestKCDTO user) {
        return this.keycloakService.createUser(user);
    }

    @PutMapping("/keycloak/users/{userId}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseEntity<?> updateUserKeycloak(@RequestBody @Valid UserRequestKCDTO user, @PathVariable String userId) {
        return this.keycloakService.updateUser(userId,user);
    }

    @DeleteMapping("/keycloak/users/{userId}")
    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    public ResponseEntity<?> deleteUserKeycloak(@PathVariable String userId) {
        return this.keycloakService.deleteUser(userId);
    }

    @PreAuthorize("hasRole('ADMIN_SCOUTING_ROLE')")
    @GetMapping("/ping-admin")
    public String pingAdmin() {
        return "hola admin";
    }

    @PreAuthorize("hasRole('USER_SCOUTING_ROLE')")
    @GetMapping("/ping-user")
    public String pingUser() {
        return "hola user";
    }

    @PreAuthorize("hasRole('USER_SCOUTING_ROLE') or hasRole('ADMIN_SCOUTING_ROLE')")
    @GetMapping("/ping-admin-user")
    public String pingAdminUser() {
        return "hola user";
    }

    @PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE')")
    @GetMapping("/ping-developer")
    public String pingDeveloper() {
        return "hola developer";
    }

    @GetMapping("")
    public List<UserResponseDTO> getAllUsers() {
        return this.userService.getAll();
    }

    @GetMapping("/by_team/{teamId}")
    public List<UserResponseDTO> getAllUsersByTeamId(@PathVariable Long teamId) {
        return this.userService.getAllByTeamId(teamId);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return this.userService.getById(id);
    }

    @PostMapping("/log-in")
    public UserResponseDTO startSession(@RequestBody @Valid LoginDTO loginDTO) {
        return this.userService.getByCredentials(loginDTO);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO user) {
        return this.userService.create(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequestDTO user, @PathVariable Long id) {
        return this.userService.update(user,id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserStatus(Boolean status, @PathVariable Long id) {
        return this.userService.updateStatus(status,id);
    }

    @PatchMapping("/subscription/{teamId}")
    public ResponseEntity<?> updateAllUsersSubscriptionStatus(Boolean status, @PathVariable Long teamId) {
        return this.userService.updateAllUsersSubscriptionStatus(status,teamId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return this.userService.delete(id);
    }

}
