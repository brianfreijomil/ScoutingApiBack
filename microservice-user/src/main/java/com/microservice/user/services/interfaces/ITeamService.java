package com.microservice.user.services.interfaces;

import com.microservice.user.model.dtos.team.request.TeamRequestDTO;
import com.microservice.user.model.dtos.team.response.TeamResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITeamService {

    ResponseEntity<List<TeamResponseDTO>> getAll();

    ResponseEntity<TeamResponseDTO> getById(Long id);

    ResponseEntity<?> create(TeamRequestDTO team);

    ResponseEntity<?> update(TeamRequestDTO team, Long id);

    ResponseEntity<?> updateTeamSubscriptionStatus(Boolean subscriptionStatus, Long id);

    ResponseEntity<?> delete(Long id);

    //PlayersByTeamResponse getPlayersByTeamId(Long teamId);

}
