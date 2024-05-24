package com.microservice.player.controllers;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.stat.request.StatRequestDTO;
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

    @GetMapping

    @PostMapping("")
    public ResponseApi createStat(@RequestBody @Valid StatRequestDTO stat) {
        return this.statService.create(stat);
    }

    @PutMapping("/{id}")
    public ResponseApi updateStat(@RequestBody @Valid StatRequestDTO stat, @PathVariable Long id) {
        return this.statService.update(stat,id);
    }

    @DeleteMapping("/{id}")
    public ResponseApi deleteStat(@PathVariable Long id) {
        return this.statService.delete(id);
    }
}
