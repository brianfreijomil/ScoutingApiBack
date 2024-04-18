package com.microservice.team.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private Long dni;
    private String surname;
    private String name;
    private String contact;
    private String category;
    private String ubication;
    private Double height;
    private String skillLeg;
    private String urlImage;
    private String firstPosition;
    private String secondPosition;
    private String valoration;
    private String characteristics;
    private ScouterDTO scouter;
    private Date dateSeen;
    private String divisionSeen;
    private String teamSeen;
    private String contactTeamSeen;
    private Boolean status;
    private List<String> videoLinks;
    private Long teamId;

}
