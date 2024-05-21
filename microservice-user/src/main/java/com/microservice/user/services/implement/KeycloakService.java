package com.microservice.user.services.implement;

import com.microservice.user.exceptions.ConflictExistException;
import com.microservice.user.exceptions.ConflictKeycloakException;
import com.microservice.user.exceptions.NotFoundException;
import com.microservice.user.http.response.ResponseApi;
import com.microservice.user.model.dtos.user.ScouterDTO;
import com.microservice.user.model.dtos.user.SessionDTO;
import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import com.microservice.user.model.events_kafka.UserEventKafka;
import com.microservice.user.repositories.TeamRepository;
import com.microservice.user.services.interfaces.IKafkaEventsService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeycloakService implements IKeycloakService {

    @Autowired
    private IKafkaEventsService kafkaService;
    @Autowired
    private TeamRepository teamRepository;


    @Override
    public ResponseApi<SessionDTO> startSession(LoginDTO loginUser) {
        return null;
    }

    /**
     * find all users of the app
     *
     * @return list of UserDTO
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseApi<List<UserResponseDTO>> findAllUsers() {
        String attributeKey = "team_id";
        List<UserRepresentation> usersKeycloak = KeycloakProvider.getRealmResource().users().list();
        List<UserResponseDTO> usersDTO = new ArrayList<>();
        if(!usersKeycloak.isEmpty()) {
            usersDTO = usersKeycloak.stream().map(user ->
                    new UserResponseDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getLastName(),
                            user.getFirstName(),
                            user.isEnabled(),
                            user.getAttributes().get(attributeKey).get(0))
            ).collect(Collectors.toList());
        }

        return new ResponseApi<>(usersDTO, HttpStatus.OK, "OK");
    }

    /**
     * get all users filter by teamId (attribute of users)
     *
     * @param teamId
     * @return list of users DTO
     * @throws NotFoundException if a team with the teamId is not found
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseApi<List<UserResponseDTO>> findAllUsersByTeamId(Long teamId) {
        if(this.teamRepository.existsById(teamId)) {
            String attributeKey = "team_id";

            List<UserRepresentation> usersKeycloak = KeycloakProvider.getRealmResource().users().list();

            List<UserResponseDTO> userList = usersKeycloak.stream()
                    .filter(user -> {
                        List<String> attributeValues = user.getAttributes().get(attributeKey);
                        return attributeValues != null && attributeValues.contains(teamId.toString());
                    })
                    .map(user -> {
                        UserResponseDTO dto = new UserResponseDTO();
                        dto.setId(user.getId());
                        dto.setUsername(user.getUsername());
                        dto.setEmail(user.getEmail());
                        dto.setFirstName(user.getFirstName());
                        dto.setLastName(user.getLastName());
                        dto.setEnabled(user.isEnabled());
                        dto.setTeamId(teamId.toString()); // Set the teamId from the filter criteria
                        return dto;
                    })
                    .collect(Collectors.toList());
            log.info("userlist: " + userList.size());
            return new ResponseApi<>(userList, HttpStatus.OK, "OK");
        }
        throw new NotFoundException("Team","ID",teamId.toString());
    }

    /**
     * find user by username
     *
     * @param username
     * @return UserResponseDTO
     * @throws NotFoundException if a user with the username is not found
     * @throws ConflictKeycloakException if an error occurs with keycloak
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseApi<UserResponseDTO> searchUserByUsername(String username) {

        List<UserRepresentation> usersKeycloak = new ArrayList<>();
        try {
            usersKeycloak = KeycloakProvider.getRealmResource().users()
                    .searchByUsername(username, true);
        }
        catch (Exception ex) {
            throw new ConflictKeycloakException(ex.getMessage());
        }
        List<UserResponseDTO> usersDTO = new ArrayList<>();
        if(!usersKeycloak.isEmpty()) {
            usersDTO = usersKeycloak.stream().map(user ->
                    new UserResponseDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getLastName(),
                            user.getFirstName(),
                            user.isEnabled(),
                            user.getAttributes().get("team_id").get(0))
            ).collect(Collectors.toList());
            //return user in first position, the only one
            return new ResponseApi<>(usersDTO.get(0), HttpStatus.OK,"OK");
        }
        throw new NotFoundException("User","username",username);
    }

    /**
     * create a user
     *
     * @param user
     * @return user creation status
     * @throws ConflictExistException if a user with the username already exists
     */
    @Override
    @Transactional
    public ResponseApi<?> createUser(UserRequestDTO user) {
        String attributeKey = "team_id";
        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        //attributes needs a map (key = string, value = list<String>)
        Map<String,List<String>> attributes = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(user.getTeamId().toString());
        attributes.put(attributeKey,values);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setAttributes(attributes);
        //create a user and get the status
        Response response = usersResource.create(userRepresentation);
        status = response.getStatus();

        //ask if the user was created (201 = created, 400 = user already created, 5xx = error server)
        if(status == 201) {
            //get userId which is at the end of the path
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            //kafkaEvent-----------------------------------------------------------------------------------
            this.kafkaService.emitKafkaEvent(new UserEventKafka("create",
                    new ScouterDTO(userId, userRepresentation.getLastName(), userRepresentation.getFirstName()))
            );
            //---------------------------------------------------------------------------------------------

            try {
                //set password
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(false);
                credentialRepresentation.setType(OAuth2Constants.PASSWORD);
                credentialRepresentation.setValue(user.getPassword());
                usersResource.get(userId).resetPassword(credentialRepresentation);

                //set roles
                RealmResource realmResource = KeycloakProvider.getRealmResource();
                List<RoleRepresentation> roleRepresentations = null;

                //if the request came without roles, set role "USER" by default
                if(user.getRoles() == null || user.getRoles().isEmpty()) {
                    roleRepresentations = List.of(realmResource.roles().get("USER").toRepresentation());
                }
                else {
                    roleRepresentations = realmResource.roles()
                            .list()
                            .stream()
                            .filter(role -> user.getRoles()
                                    .stream()
                                    .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                            .toList();
                }
                //add roles
                realmResource.users()
                        .get(userId)
                        .roles()
                        .realmLevel()
                        .add(roleRepresentations);

                return new ResponseApi<>(userId, HttpStatus.CREATED,"OK");
            }
            catch (Exception ex) {
                throw new ConflictKeycloakException(ex.getMessage());
            }
        }
        else if (status == 409) //the user already exists (is not perfect)
            throw new ConflictExistException("User","username",user.getUsername());
        else //keycloak error
            throw new ConflictKeycloakException(response.getStatusInfo().toString());

    }

    /**
     * delete a user by id
     *
     * @param userId
     * @return user elimination status
     * @throws ConflictKeycloakException if an error occurs with keycloak
     */
    @Override
    @Transactional
    public ResponseApi<?> deleteUser(String userId) {
        try {
            UserRepresentation userRepresentation = KeycloakProvider.getUserResource().get(userId).toRepresentation();
            KeycloakProvider.getUserResource().get(userId).remove();

            //kafkaEvent-----------------------------------------------------------------------------------
            this.kafkaService.emitKafkaEvent(new UserEventKafka("delete",
                    new ScouterDTO(userId, userRepresentation.getLastName(), userRepresentation.getFirstName()))
            );
            //---------------------------------------------------------------------------------------------

            return new ResponseApi<>(true,HttpStatus.OK,"OK");
        }
        catch (Exception ex) {
            throw new ConflictKeycloakException(ex.getMessage());
        }
    }

    /**
     * update a user by id
     *
     * @param userId
     * @param user
     * @return user editing status
     * @throws ConflictKeycloakException if an error occurs with keycloak
     */
    @Override
    @Transactional
    public ResponseApi<?> updateUser(String userId, UserRequestDTO user) {

        //THE USERNAME CANNOT BE UPDATED!!!

        //update password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());

        //update user data
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        //set credentials
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        try {
            UserResource userResource = KeycloakProvider.getUserResource().get(userId);
            userResource.update(userRepresentation);

            //kafkaEvent-----------------------------------------------------------------------------------
            this.kafkaService.emitKafkaEvent(new UserEventKafka("update",
                    new ScouterDTO(userId, userRepresentation.getLastName(), userRepresentation.getFirstName()))
            );
            //---------------------------------------------------------------------------------------------

            return new ResponseApi<>(true,HttpStatus.ACCEPTED,"OK");
        }
        catch (Exception ex) {
            throw new ConflictKeycloakException(ex.getMessage());
        }
    }
}
