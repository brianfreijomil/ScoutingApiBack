package com.microservice.user.controllers;

import com.microservice.user.dtos.user.request.LoginDTO;
import com.microservice.user.dtos.user.request.UserRequestDTO;
import com.microservice.user.dtos.user.response.UserResponseDTO;
import com.microservice.user.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

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

    @GetMapping("/login")
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
