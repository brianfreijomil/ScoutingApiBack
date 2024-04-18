package com.microservice.team.dtos.team.response;

import com.microservice.team.dtos.team.request.TeamRequestDTO;
import com.microservice.team.entities.Team;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
