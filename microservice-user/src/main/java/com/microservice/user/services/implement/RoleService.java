package com.microservice.user.services.implement;

import com.microservice.user.dtos.role.request.RoleRequestDTO;
import com.microservice.user.dtos.role.response.RoleResponseDTO;
import com.microservice.user.entities.Role;
import com.microservice.user.exceptions.ConflictExistException;
import com.microservice.user.exceptions.ConflictPersistException;
import com.microservice.user.exceptions.NotFoundException;
import com.microservice.user.repositories.RoleRepository;
import com.microservice.user.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAll() {
        return this.roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponseDTO(role))
                .collect(Collectors.toList());
    }

    /*
    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO getById(Long id) {
        Optional<Role> role = this.roleRepository.findById(id);
        if(!role.isEmpty()) {
            return new RoleResponseDTO(role.get());
        }
        throw new NotFoundException("Role","ID",id.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO getByType(String type) {
        Role role = this.roleRepository.findByType(type);
        if(role != null) {
            return new RoleResponseDTO(role);
        }
        throw new NotFoundException("Role","type",role.getType());
    }
    */

    @Override
    @Transactional
    public ResponseEntity<?> create(RoleRequestDTO role) {
        if(this.roleRepository.findByType(role.getRoleName()) != null) {
            try {
                this.roleRepository.save(new Role(role));
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("create","Role","role_name", role.getRoleName(), ex.getMessage());
            }
        }
        throw new ConflictExistException("Role","role_name", role.getRoleName());
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(RoleRequestDTO role, Long id) {
        Optional<Role> roleExisting = this.roleRepository.findById(id);
        if(!roleExisting.isEmpty()) {
            try {
                roleExisting.get().setRoleName(role.getRoleName());
                //the id cannot be edited
                this.roleRepository.save(roleExisting.get());
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Role","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Role","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Role> roleExisting = this.roleRepository.findById(id);
        if(!roleExisting.isEmpty()) {
            try {
                //ANTES DE ELIMINAR ESTE ROLE DEBO CAMBIARLO EN LOS USUARIOS
                //QUE QUE ESTEN RELACIONADOS
                this.roleRepository.delete(roleExisting.get());
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","Role","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Role","ID",id.toString());
    }
}
