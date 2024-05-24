package com.microservice.player.controllers;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.model.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.model.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.model.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.model.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.services.interfaces.IPlayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @GetMapping("/by_team")
    public ResponseApi<List<PlayerSearchDTO>> getAllPlayersByTeamId(@NotNull @NotEmpty Long team_id ) {
        return this.playerService.getAllByTeamId(team_id);
    }

    @GetMapping("/by_fullname")
    public ResponseApi<PlayerResponseDTO> getPlayerByFullName(@NotNull @NotEmpty String full_name ) {
        return this.playerService.getByFullName(full_name);
    }

    @GetMapping("/{id}")
    public ResponseApi<PlayerResponseDTO> getPlayerById(@PathVariable Long id) {
        return this.playerService.getById(id);
    }

    @PostMapping("")
    public ResponseApi<?> createPlayer(@RequestBody @Valid PlayerRequestDTO player) {
        return this.playerService.create(player);
    }

    @PutMapping("/{id}")
    public ResponseApi<?> updatePlayer(@RequestBody @Valid PlayerRequestDTO player, @PathVariable Long id) {
        return this.playerService.update(player, id);
    }

    @PatchMapping("/{id}")
    public ResponseApi<?> updatePlayerMultimedia(@RequestBody @Valid PlayerMultimediaDTO multimediaDTO, @PathVariable Long id) {
        return this.playerService.updateMultimedia(multimediaDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseApi<?> deletePlayer(@PathVariable Long id) {
       return this.playerService.delete(id);
    }

}
