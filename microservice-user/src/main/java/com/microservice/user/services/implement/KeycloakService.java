package com.microservice.user.services.implement;

import com.microservice.user.exceptions.ConflictExistException;
import com.microservice.user.exceptions.ConflictPersistException;
import com.microservice.user.model.dtos.user.request.UserRequestKCDTO;
import com.microservice.user.services.interfaces.IKeycloakService;
import com.microservice.user.utils.KeycloakProvider;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class KeycloakService implements IKeycloakService {

    //TODOS LOS USUARIOS DE KEYCLOAK
    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getRealmResource()
                .users().list();
    }

    //BUSCAR USUARIO POR SU USERNAME
    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloakProvider.getRealmResource()
                .users().searchByUsername(username, true);
    }

    //CREAR UN NUEVO USUARIO EN KEYCLOAK
    @Override
    public ResponseEntity<?> createUser(UserRequestKCDTO user) {

        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getSurname());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        Response response = usersResource.create(userRepresentation);
        status = response.getStatus();

        //consulto si se creo el usuario
        if(status == 201) {
            //busco el usuario que esta al final de la url
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            //seteo password
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(user.getPassword());

            usersResource.get(userId).resetPassword(credentialRepresentation);

            //seteo roles
            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<RoleRepresentation> roleRepresentations = null;

            for (RoleRepresentation r:realmResource.roles().list()) {
                log.info(r.getName());
            }

            //si no traje roles asigno role de usuario por default
            if(user.getRoles() == null || user.getRoles().isEmpty()) {
                roleRepresentations = List.of(realmResource.roles().get("USER").toRepresentation());
            }
            else {
                //obtengo los roles de keycloak y busco por cada uno cual de los roles que traje en el request coincide,
                // todos los roles del request que coincidan con los roles de keycloak los guardo
                //recorro la lista de roles de keycloak, por cada uno recorro la lista de roles del requesst,
                // role del request que haga match con el role de keycloak se guarda
                roleRepresentations = realmResource.roles()
                        .list()
                        .stream()
                        .filter(role -> user.getRoles()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                        .toList();
            }
            //agrego roles
            realmResource.users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(roleRepresentations);

            return new ResponseEntity<>(true, HttpStatus.CREATED);

        } else if (status == 400) {
            //ya existe el usuairo
            log.error("User exist already");
            throw new ConflictExistException("User","username",user.getUsername());

        }
        else {
            //error del server
            log.error("error creando el usuario");
            throw new ConflictPersistException("create","User","username", user.getUsername(), "unknow");
        }

    }

    //BORRAR UN USUARIO DE KEYCLOAK
    @Override
    public ResponseEntity<?> deleteUser(String userId) {
        KeycloakProvider.getUserResource().get(userId).remove();
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    //ACTUALIZAR UN USUARIO DE KEYCLOAK
    @Override
    public ResponseEntity<?> updateUser(String userId, UserRequestKCDTO user) {

        //cambio password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());

        //modifico user
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getSurname());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        //seteo credenciales
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UserResource userResource = KeycloakProvider.getUserResource().get(userId);
        userResource.update(userRepresentation);

        return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
    }
}
