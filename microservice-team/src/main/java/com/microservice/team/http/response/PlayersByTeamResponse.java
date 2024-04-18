package com.microservice.team.http.response;

import com.microservice.team.dtos.player.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayersByTeamResponse {

    private String teamName;
    private List<PlayerDTO> playerDTOList;

}
