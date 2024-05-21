package com.microservice.user.services.interfaces;

import com.microservice.user.http.response.ResponseApi;
import com.microservice.user.model.dtos.team.request.TeamRequestDTO;
import com.microservice.user.model.dtos.team.response.TeamResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITeamService {

    ResponseApi<List<TeamResponseDTO>> getAll();

    ResponseApi<TeamResponseDTO> getById(Long id);

    ResponseApi<?> create(TeamRequestDTO team);

    ResponseApi<?> update(TeamRequestDTO team, Long id);

    ResponseApi<?> updateTeamSubscriptionStatus(Boolean subscriptionStatus, Long id);

    ResponseApi<?> delete(Long id);

    //PlayersByTeamResponse getPlayersByTeamId(Long teamId);

}
