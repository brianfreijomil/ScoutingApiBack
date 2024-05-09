package com.microservice.player.model.dtos.player.request;

import com.microservice.player.model.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.model.entities.Scouter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequestDTO {

    @NotNull(message = "dni cannot be null")
    private Long dni;
    @NotNull(message = "surname cannot be null")
    @NotEmpty(message = "surname cannot be empty")
    private String surname;
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull(message = "contact cannot be null")
    @NotEmpty(message = "contact cannot be empty")
    private String contact;
    @NotNull(message = "category cannot be null")
    @NotEmpty(message = "category cannot be empty")
    private String category;
    @NotNull(message = "ubication cannot be null")
    @NotEmpty(message = "ubication cannot be empty")
    private String ubication;
    private Double height;
    private String skillLeg;
    private String urlImage;
    @NotNull(message = "firstPosition cannot be null")
    @NotEmpty(message = "firstPosition cannot be empty")
    private String firstPosition;
    private String secondPosition;
    private String valoration;
    private String characteristics;
    @NotNull(message = "scouter cannot be null")
    private ScouterRequestDTO scouter;
    @NotNull(message = "dateSeen cannot be null")
    private Date dateSeen;
    private Time timeSeen;
    @NotNull(message = "divisionSeen cannot be null")
    @NotEmpty(message = "divisionSeen cannot be empty")
    private String divisionSeen;
    @NotNull(message = "teamSeen cannot be null")
    @NotEmpty(message = "teamSeen cannot be empty")
    private String teamSeen;
    @NotNull(message = "campSeen cannot be null")
    @NotEmpty(message = "campSeen cannot be empty")
    private String campSeen;
    @NotNull(message = "contactTeamSeen cannot be null")
    @NotEmpty(message = "contactTeamSeen cannot be empty")
    private String contactTeamSeen;
    @NotNull(message = "status cannot be null")
    private Boolean status;
    //@Column
    //private List<String> videoLinks;
    @NotNull(message = "teamId cannot be null")
    private Long teamId;
}
