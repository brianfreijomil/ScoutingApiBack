package com.microservice.player.controllers;

import com.microservice.player.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.entities.Player;
import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.services.interfaces.IPlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @GetMapping("/all/{teamId}")
    public List<PlayerSearchDTO> getAllPlayersByTeamId(@PathVariable Long teamId) {
        return this.playerService.getAllByTeamId(teamId);
    }

    @GetMapping("/{id}")
    public PlayerResponseDTO getPlayerById(@PathVariable Long id) {
        return this.playerService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> createPlayer(@RequestBody @Valid PlayerRequestDTO player) {
        return this.playerService.create(player);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@RequestBody @Valid PlayerRequestDTO player, @PathVariable Long id) {
        return this.playerService.update(player, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePlayerMultimedia(@RequestBody @Valid PlayerMultimediaDTO multimediaDTO, @PathVariable Long id) {
        return this.playerService.updateMultimedia(multimediaDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
       return this.playerService.delete(id);
    }

    /* ----- Scouter Context ----- */

    @PostMapping("/scouter")
    public ResponseEntity<?> createScouter(@RequestBody @Valid ScouterRequestDTO scouter) {
        return this.playerService.createScouter(scouter);
    }

    @PutMapping("/scouter/{scouterId}")
    public ResponseEntity<?> updateScouter(@RequestBody @Valid ScouterRequestDTO scouter, @PathVariable Long scouterId) {
        return this.playerService.updateScouter(scouter,scouterId);
    }

    @DeleteMapping("/scouter/{scouterId}")
    public ResponseEntity<?> deleteScouter(@PathVariable Long scouterId) {
        return this.playerService.deleteScouter(scouterId);
    }

}
