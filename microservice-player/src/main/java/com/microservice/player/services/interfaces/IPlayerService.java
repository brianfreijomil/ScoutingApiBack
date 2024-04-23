package com.microservice.player.services.interfaces;

import com.microservice.player.model.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.model.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.model.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.model.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.model.dtos.scouter.request.ScouterRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPlayerService {

    List<PlayerSearchDTO> getAllByTeamId(Long teamId);

    PlayerResponseDTO getById(Long id);

    ResponseEntity<?> create(PlayerRequestDTO player);

    ResponseEntity<?> update(PlayerRequestDTO player, Long id);

    ResponseEntity<?> updateMultimedia(PlayerMultimediaDTO multimedia, Long id);

    ResponseEntity<?> delete(Long id);

    boolean createScouter(ScouterRequestDTO scouter);

    boolean updateScouter(ScouterRequestDTO scouter);

    boolean deleteScouter(ScouterRequestDTO scouter);



}