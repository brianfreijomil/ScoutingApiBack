package com.microservice.player.dtos.stat.response;

import com.microservice.player.entities.Stat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class StatResponseDTO {

    private Long id;
    private Date dateRegister;
    private Long matches;
    private Double minutes;
    private Long scores;
    private Long assists;
    private Long yellowCards;
    private Long redCards;
    private Long playerId;

    public StatResponseDTO(Stat stat) {
        this.id = stat.getId();
        this.dateRegister = stat.getDateRegister();
        this.matches = stat.getMatches();
        this.minutes = stat.getMinutes();
        this.scores = stat.getScores();
        this.assists = stat.getAssists();
        this.yellowCards = stat.getYellowCards();
        this.redCards = stat.getRedCards();
        this.playerId = stat.getPlayer().getDni();
    }
}
