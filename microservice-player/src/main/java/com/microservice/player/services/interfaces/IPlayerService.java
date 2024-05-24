package com.microservice.player.services.interfaces;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.model.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.model.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.model.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.model.dtos.scouter.request.ScouterRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPlayerService {

    ResponseApi<List<PlayerSearchDTO>> getAllByTeamId(Long teamId);

    ResponseApi<PlayerResponseDTO> getByFullName(String fullName);

    ResponseApi<PlayerResponseDTO> getById(Long id);

    ResponseApi<?> create(PlayerRequestDTO player);

    ResponseApi<?> update(PlayerRequestDTO player, Long id);

    ResponseApi<?> updateMultimedia(PlayerMultimediaDTO multimedia, Long id);

    ResponseApi<?> delete(Long id);

    boolean createScouter(ScouterRequestDTO scouter);

    boolean updateScouter(ScouterRequestDTO scouter);

    boolean deleteScouter(ScouterRequestDTO scouter);



}