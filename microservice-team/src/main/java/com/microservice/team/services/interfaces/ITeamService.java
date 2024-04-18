package com.microservice.team.services.interfaces;

import com.microservice.team.dtos.team.request.TeamRequestDTO;
import com.microservice.team.dtos.team.request.TeamSubscriptionStatusDTO;
import com.microservice.team.dtos.team.response.TeamResponseDTO;
import com.microservice.team.entities.Team;
import com.microservice.team.http.response.PlayersByTeamResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITeamService {

    List<TeamResponseDTO> getAll();

    TeamResponseDTO getById(Long id);

    ResponseEntity<?> create(TeamRequestDTO team);

    ResponseEntity<?> update(TeamRequestDTO team, Long id);

    ResponseEntity<?> updateTeamSubscriptionStatus(TeamSubscriptionStatusDTO subscriptionStatusDTO, Long id);

    ResponseEntity<?> delete(Long id);

    PlayersByTeamResponse getPlayersByTeamId(Long teamId);

}
