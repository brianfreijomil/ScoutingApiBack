package com.microservice.user.model.dtos.team.response;

import com.microservice.user.model.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private Boolean subscribed;
    private Date dateOfExpired;

    public TeamResponseDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.email = team.getEmail();
        this.contactNumber = team.getContactNumber();
        this.subscribed = team.getSubscribed();
        this.dateOfExpired = team.getDateOfExpired();
    }
}
