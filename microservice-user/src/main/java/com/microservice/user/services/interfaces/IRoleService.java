package com.microservice.user.services.interfaces;

import com.microservice.user.model.dtos.role.request.RoleRequestDTO;
import com.microservice.user.model.dtos.role.response.RoleResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoleService {

    List<RoleResponseDTO> getAll();

    //RoleResponseDTO getById(Long id);

    //RoleResponseDTO getByType(String type);

    ResponseEntity<?> create(RoleRequestDTO role);

    ResponseEntity<?> update(RoleRequestDTO role,Long id);

    ResponseEntity<?> delete(Long id);

}
