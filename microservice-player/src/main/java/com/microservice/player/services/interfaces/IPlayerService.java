package com.microservice.player.services.interfaces;

import com.microservice.player.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.entities.Player;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPlayerService {

    List<PlayerSearchDTO> getAllByTeamId(Long teamId);

    PlayerResponseDTO getById(Long id);

    ResponseEntity<?> create(PlayerRequestDTO player);

    ResponseEntity<?> update(PlayerRequestDTO player, Long id);

    ResponseEntity<?> updateMultimedia(PlayerMultimediaDTO multimedia, Long id);

    ResponseEntity<?> delete(Long id);

    ResponseEntity<?> createScouter(ScouterRequestDTO scouter);

    ResponseEntity<?> updateScouter(ScouterRequestDTO scouter, Long id);

    ResponseEntity<?> deleteScouter(Long id);



}