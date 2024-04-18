package com.microservice.player.controllers;

import com.microservice.player.dtos.stat.request.StatRequestDTO;
import com.microservice.player.entities.Player;
import com.microservice.player.entities.Stat;
import com.microservice.player.services.interfaces.IStatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
public class StatController {

    @Autowired
    private IStatService statService;

    @PostMapping("")
    public ResponseEntity createStat(@RequestBody @Valid StatRequestDTO stat) {
        return this.statService.create(stat);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStat(@RequestBody @Valid StatRequestDTO stat, @PathVariable Long id) {
        return this.statService.update(stat,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStat(@PathVariable Long id) {
        return this.statService.delete(id);
    }
}
