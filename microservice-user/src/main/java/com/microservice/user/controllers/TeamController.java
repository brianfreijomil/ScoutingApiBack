package com.microservice.user.controllers;

import com.microservice.user.http.response.ResponseApi;
import com.microservice.user.model.dtos.team.request.TeamRequestDTO;
import com.microservice.user.model.dtos.team.response.TeamResponseDTO;
import com.microservice.user.services.interfaces.ITeamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@PreAuthorize("hasRole('DEVELOPER_SCOUTING_ROLE')")
public class TeamController {

    @Autowired
    private ITeamService teamService;

    @GetMapping("")
    public ResponseApi<List<TeamResponseDTO>> getAllTeams() {
        return this.teamService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseApi<TeamResponseDTO> getTeamById(@PathVariable Long id) {
        return this.teamService.getById(id);
    }

    /*
    @GetMapping("/{id}/players")
    public PlayersByTeamResponse getAllPlayersByTeamId(@PathVariable Long id) {
        return this.teamService.getPlayersByTeamId(id);
    }
     */

    @PostMapping("")
    public ResponseApi<?> createTeam(@RequestBody @Valid TeamRequestDTO team) {
        return this.teamService.create(team);
    }

    @PutMapping("/{id}")
    public ResponseApi<?> updateTeam(@RequestBody @Valid TeamRequestDTO team, @PathVariable Long id) {
        return this.teamService.update(team, id);
    }

    @GetMapping("/{id}/subscription")
    public ResponseApi<?> updateTeamSubscriptionStatus(@NotNull @NotEmpty Boolean sub, @PathVariable Long id) {
        return this.teamService.updateTeamSubscriptionStatus(sub,id);
    }

    @DeleteMapping("/{id}")
    public ResponseApi<?> deleteTeam(@PathVariable Long id) {
        return this.teamService.delete(id);
    }

}
