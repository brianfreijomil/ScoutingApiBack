package com.microservice.team.client;

import com.microservice.team.dtos.player.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-player", url = "localhost:8080/api/players")
public interface PlayerClient {

    @GetMapping("/all/{teamId}")
    List<PlayerDTO> getAllPlayers(@PathVariable Long teamId);

}
