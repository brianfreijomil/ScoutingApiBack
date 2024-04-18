package com.microservice.team.services.implement;

import com.microservice.team.client.PlayerClient;
import com.microservice.team.dtos.player.PlayerDTO;
import com.microservice.team.dtos.team.request.TeamRequestDTO;
import com.microservice.team.dtos.team.request.TeamSubscriptionStatusDTO;
import com.microservice.team.dtos.team.response.TeamResponseDTO;
import com.microservice.team.entities.Team;
import com.microservice.team.exceptions.ConflictExistException;
import com.microservice.team.exceptions.ConflictPersistException;
import com.microservice.team.exceptions.NotFoundException;
import com.microservice.team.http.response.PlayersByTeamResponse;
import com.microservice.team.repositories.TeamRepository;
import com.microservice.team.services.interfaces.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService implements ITeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerClient playerClient;

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponseDTO> getAll() {
        return this.teamRepository.findAll()
                .stream()
                .map(team -> new TeamResponseDTO(team))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponseDTO getById(Long id) {
        Optional<Team> team = this.teamRepository.findById(id);
        if(!team.isEmpty()) {
            return new TeamResponseDTO(team.get());
        }
        throw new NotFoundException("Team","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(TeamRequestDTO team) {
        if(this.teamRepository.existsByEmail(team.getEmail())) {
            try {
                this.teamRepository.save(new Team(team));
                return new ResponseEntity<>(true,HttpStatus.CREATED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("create","Team","email", team.getEmail(), ex.getMessage());
            }
        }
        throw new ConflictExistException("Team","email", team.getEmail());
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(TeamRequestDTO team, Long id) {
        Optional<Team> teamExisting = this.teamRepository.findById(id);
        if(!teamExisting.isEmpty()) {
            try {
                teamExisting.get().setName(team.getName());
                teamExisting.get().setEmail(team.getEmail());
                teamExisting.get().setContactNumber(team.getContactNumber());
                teamExisting.get().setSubscribed(team.getSubscribed());
                teamExisting.get().setDateOfExpired(team.getDateOfExpired());
                //the id cannot be edited
                this.teamRepository.save(teamExisting.get());
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Team","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Team","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateTeamSubscriptionStatus(TeamSubscriptionStatusDTO subscriptionStatusDTO, Long id) {
        Optional<Team> teamExisting = this.teamRepository.findById(id);
        if(!teamExisting.isEmpty()) {
            try {
                teamExisting.get().setSubscribed(subscriptionStatusDTO.getSubscribed());

                //si el nuevo estado es true, habilito a los usuarios del team para utilizar la app
                //si el nuevo estado es false, deshabilito a los usuarios del team para utilizar la app

                this.teamRepository.save(teamExisting.get());
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("updateSubscription","Team","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Team","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Team> teamExisting = this.teamRepository.findById(id);
        if(!teamExisting.isEmpty()) {
            try {
                //PRIMERO DEBERIA ELIMINAR TODA INFO RELACIONADA A ESTE EQUIPO
                //DESDE JUGADORES, USUARIOS, ESTADISTICAS, REPORTES CLINICOS
                //INFORMACION DE CALENDARIO, MAPA, ETC, ETC
                this.teamRepository.delete(teamExisting.get());
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete", "Team", "ID", id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Team","ID",id.toString());
    }


    // -------------------- EJEMPLO USANDO FEIGN CLIENT ------------------------ //
    @Override
    @Transactional
    public PlayersByTeamResponse getPlayersByTeamId(Long id) {
        //consultar team
        Team team = this.teamRepository.findById(id).get();

        //obtener los players
        List<PlayerDTO> playerDTOList = this.playerClient.getAllPlayers(id);

        return PlayersByTeamResponse.builder()
                .teamName(team.getName())
                .playerDTOList(playerDTOList)
                .build();
    }
}
