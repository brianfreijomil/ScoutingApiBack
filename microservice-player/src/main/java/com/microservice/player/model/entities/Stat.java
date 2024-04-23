package com.microservice.player.model.entities;

import com.microservice.player.model.dtos.stat.request.StatRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stats")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date dateRegister;

    @Column
    private Long matches;

    @Column
    private Double minutes;

    @Column
    private Long scores;

    @Column
    private Long assists;

    @Column
    private Long yellowCards;

    @Column
    private Long redCards;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    public Stat(StatRequestDTO requestDTO, Player player) {
        this.dateRegister = requestDTO.getDateRegister();
        this.matches = requestDTO.getMatches();
        this.minutes = requestDTO.getMinutes();
        this.scores = requestDTO.getScores();
        this.assists = requestDTO.getAssists();
        this.yellowCards = requestDTO.getYellowCards();
        this.redCards = requestDTO.getRedCards();
        this.player = player;
    }
}
