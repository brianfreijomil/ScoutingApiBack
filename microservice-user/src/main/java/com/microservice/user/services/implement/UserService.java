package com.microservice.user.services.implement;

import com.microservice.user.model.dtos.ScouterDTO;
import com.microservice.user.model.dtos.user.request.LoginDTO;
import com.microservice.user.model.dtos.user.request.UserRequestDTO;
import com.microservice.user.model.dtos.user.response.UserResponseDTO;
import com.microservice.user.model.entities.Role;
import com.microservice.user.model.entities.User;
import com.microservice.user.exceptions.ConflictExistException;
import com.microservice.user.exceptions.ConflictPersistException;
import com.microservice.user.exceptions.NotFoundException;
import com.microservice.user.model.events_kafka.UserEventKafka;
import com.microservice.user.repositories.RoleRepository;
import com.microservice.user.repositories.UserRepository;
import com.microservice.user.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private KafkaEventsService kafkaService;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllByTeamId(Long teamId) {
        return this.userRepository.findAllByTeamId(teamId)
                .stream()
                .map(user -> new UserResponseDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(!user.isEmpty()) {
            return new UserResponseDTO(user.get());
        }
        throw new NotFoundException("User","ID",id.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getByCredentials(LoginDTO loginDTO) {
        User user = this.userRepository.findByUsername(loginDTO.getUsername());
        if(user != null) {
            //SI CUMPLE LA CONTRASEÑA RETORNO EL USUARIO
            return new UserResponseDTO(user);
        }
        throw new NotFoundException("User","username",loginDTO.getUsername());
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(UserRequestDTO user) {
        if(this.userRepository.findByUsername(user.getUsername()) == null) {
            try {
                Role role = this.roleRepository.findByType(user.getRoleDescription());
                if(role != null) {
                    User userCreated = this.userRepository.save(new User(user,role));

                    //kafkaEvent--------------------------------------------------
                    this.kafkaService.emitKafkaEvent(
                            "user-to-scouter-topic",
                            new UserEventKafka("create", new ScouterDTO(
                                            userCreated.getId(),
                                            userCreated.getSurname(),
                                            userCreated.getName()))
                    );
                    //------------------------------------------------------------
                    return new ResponseEntity<>(true, HttpStatus.CREATED);
                }
                throw new NotFoundException("Role","type", user.getRoleDescription());
            }
            catch (Exception ex) {
                throw new ConflictPersistException("create","User","username", user.getUsername(), ex.getMessage());
            }
        }
        throw new ConflictExistException("User","username",user.getSurname());
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(UserRequestDTO user, Long id) {
        //1 if : if user exist by provided ID
        //2 if : if new username is equals at the old username or
        // new username is not equals at the old username and not exists a user with the new username
        //3 if : if role exist by provided roleDescription
        Optional<User> userExisting = this.userRepository.findById(id);
        if(!userExisting.isEmpty()) {
            String oldUsername = userExisting.get().getUsername();
            if(
                    user.getUsername().equals(oldUsername) ||
                    (!user.getUsername().equals(oldUsername) && this.userRepository.findByUsername(user.getUsername()) == null)
            )
            {
                Role role = this.roleRepository.findByType(user.getRoleDescription());
                if(role != null) {
                    try {
                        userExisting.get().setUsername(user.getUsername());
                        userExisting.get().setSurname(user.getSurname());
                        userExisting.get().setName(user.getName());
                        userExisting.get().setPassword(user.getPassword());
                        userExisting.get().setRole(role);
                        userExisting.get().setEnabled(user.getEnabled());
                        //Id, subscriptionStatus and teamId cannot be edited

                        this.userRepository.save(userExisting.get());

                        //kafkaEvent--------------------------------------------------
                        this.kafkaService.emitKafkaEvent(
                                "user-to-scouter-topic",
                                new UserEventKafka("update", new ScouterDTO(
                                        userExisting.get().getId(),
                                        userExisting.get().getSurname(),
                                        userExisting.get().getName()))
                        );
                        //------------------------------------------------------------
                        return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
                    }
                    catch (Exception ex) {
                        throw new ConflictPersistException("update","User","ID",id.toString(), ex.getMessage());
                    }
                }
                throw new NotFoundException("Role","type",user.getRoleDescription());
            }
            throw new ConflictExistException("User","username",user.getUsername());
        }
        throw new NotFoundException("User","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateStatus(Boolean isEnabled, Long id) {
        Optional<User> userExisting = this.userRepository.findById(id);
        if(!userExisting.isEmpty()) {
            try {
                userExisting.get().setEnabled(isEnabled);

                this.userRepository.save(userExisting.get());
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("updateStatus","User","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("User","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateAllUsersSubscriptionStatus(Boolean status, Long teamId) {
        try {
            this.userRepository.updateUserSubscriptionStatusByTeamId(status,teamId);
            return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        }
        catch (Exception ex) {
            throw new ConflictPersistException(
                    "updateSubscriptionStatus",
                    "User",
                    "teamId",
                    teamId.toString(),
                    ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<User> userExisting = this.userRepository.findById(id);
        if(!userExisting.isEmpty()) {
            try {
                this.userRepository.delete(userExisting.get());

                //kafkaEvent--------------------------------------------------
                //surname and name are not important
                this.kafkaService.emitKafkaEvent(
                        "user-to-scouter-topic",
                        new UserEventKafka("delete", new ScouterDTO(
                                userExisting.get().getId(),
                                userExisting.get().getSurname(),
                                userExisting.get().getName()))
                );
                //------------------------------------------------------------
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","User","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("User","ID",id.toString());
    }
}
