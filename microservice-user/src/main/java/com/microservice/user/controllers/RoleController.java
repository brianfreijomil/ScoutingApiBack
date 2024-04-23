package com.microservice.user.controllers;

import com.microservice.user.model.dtos.role.request.RoleRequestDTO;
import com.microservice.user.model.dtos.role.response.RoleResponseDTO;
import com.microservice.user.services.interfaces.IRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("")
    public List<RoleResponseDTO> getAllRoles() {
        return this.roleService.getAll();
    }

    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleRequestDTO requestDTO) {
        return this.roleService.create(requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@RequestBody @Valid RoleRequestDTO requestDTO, @PathVariable Long id) {
        return this.roleService.update(requestDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        return this.roleService.delete(id);
    }

    /*
    @GetMapping("/{id}")
    public RoleResponseDTO getRoleById(@PathVariable Long id) {
        return this.roleService.getById(id);
    }

    @GetMapping("/{type}")
    public RoleResponseDTO getRoleByType(@PathVariable String type) {
        return this.roleService.getByType(type);
    }
    */

}
