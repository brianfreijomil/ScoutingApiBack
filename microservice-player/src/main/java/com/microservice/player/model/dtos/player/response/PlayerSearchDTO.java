package com.microservice.player.model.dtos.player.response;

import com.microservice.player.model.entities.Scouter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSearchDTO {

    private Long dni;
    private String surname;
    private String name;
    private String category;
    private Boolean status;
    private Scouter scouter;

}
