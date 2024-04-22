package com.microservice.team.entities;

import com.microservice.team.dtos.team.request.TeamRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "contact_number")
    private String contactNumber;

    @Column(nullable = false)
    private Boolean subscribed;

    @Column(nullable = false,name = "date_of_expired")
    private Date dateOfExpired;

    public Team(TeamRequestDTO requestDTO) {
        this.name = requestDTO.getName();
        this.email = requestDTO.getEmail();
        this.contactNumber = requestDTO.getContactNumber();
        this.subscribed = requestDTO.getSubscribed();
        this.dateOfExpired = requestDTO.getDateOfExpired();
    }
}
