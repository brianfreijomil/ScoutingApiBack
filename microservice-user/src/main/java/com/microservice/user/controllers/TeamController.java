package com.microservice.user.controllers;

import com.microservice.user.model.dtos.team.request.TeamRequestDTO;
import com.microservice.user.model.dtos.team.response.TeamResponseDTO;
import com.microservice.user.services.interfaces.ITeamService;
import jakarta.validation.Valid;
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
    public List<TeamResponseDTO> getAllTeams() {
        return this.teamService.getAll();
    }

    @GetMapping("/{id}")
    public TeamResponseDTO getTeamById(@PathVariable Long id) {
        return this.teamService.getById(id);
    }

    /*
    @GetMapping("/{id}/players")
    public PlayersByTeamResponse getAllPlayersByTeamId(@PathVariable Long id) {
        return this.teamService.getPlayersByTeamId(id);
    }
     */

    @PostMapping("")
    public ResponseEntity<?> createTeam(@RequestBody @Valid TeamRequestDTO team) {
        return this.teamService.create(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@RequestBody @Valid TeamRequestDTO team, @PathVariable Long id) {
        return this.teamService.update(team, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTeamSubscriptionStatus(@NotNull Boolean subscriptionStatus, @PathVariable Long id) {
        return this.teamService.updateTeamSubscriptionStatus(subscriptionStatus,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        return this.teamService.delete(id);
    }

}
