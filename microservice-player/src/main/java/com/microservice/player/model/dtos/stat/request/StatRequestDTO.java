package com.microservice.player.model.dtos.stat.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatRequestDTO {
    //can be null
    private Long id;

    @NotNull(message = "dateRegister cannot be null")
    private Date dateRegister;

    @NotNull(message = "matches cannot be null")
    @Min(value = 0, message = "matches cannot be less than 0")
    private Long matches;

    @NotNull(message = "minutes cannot be null")
    @Min(value = 0, message = "minutes cannot be less than 0")
    private Double minutes;

    @NotNull(message = "scores cannot be null")
    @Min(value = 0, message = "scores cannot be less than 0")
    private Long scores;

    @NotNull(message = "assists cannot be null")
    @Min(value = 0, message = "assists cannot be less than 0")
    private Long assists;

    @NotNull(message = "yellowCards cannot be null")
    @Min(value = 0, message = "yellowCards cannot be less than 0")
    private Long yellowCards;

    @NotNull(message = "redCards cannot be null")
    @Min(value = 0, message = "redCards cannot be less than 0")
    private Long redCards;

    @NotNull(message = "playerId cannot be null")
    private Long playerId;
}
