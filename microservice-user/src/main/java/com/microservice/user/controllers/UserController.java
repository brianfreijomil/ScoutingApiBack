package com.microservice.user.controllers;

import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import com.microservice.user.services.interfaces.IUserService;
import jakarta.validation.Valid;
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

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping("/ping")
    public String ping() {
        SecurityContext context = (SecurityContext) SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Scopes: " + authentication.getAuthorities();
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
