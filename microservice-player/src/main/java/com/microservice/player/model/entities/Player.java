package com.microservice.player.model.entities;

import com.microservice.player.model.dtos.player.request.PlayerRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players")
public class Player {

    @Id
    private Long dni;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String ubication;

    @Column
    private Double height;

    @Column(name = "skill_leg")
    private String skillLeg;

    @Column(name = "url_image")
    private String urlImage;

    @Column(nullable = false,name = "first_position")
    private String firstPosition;

    @Column(name = "second_position")
    private String secondPosition;

    @Column
    private String valoration;

    @Column
    private String characteristics;

    @ManyToOne(fetch = FetchType.LAZY)
    private Scouter scouter;

    @Column(nullable = false)
    private Date dateSeen;

    @Column(nullable = false)
    private String divisionSeen;

    @Column(nullable = false)
    private String teamSeen;

    @Column(nullable = false)
    private String campSeen;

    @Column
    private String contactTeamSeen;

    @Column(nullable = false) //fichado=1/obsrvado=0
    private Boolean status;

    @Column(name = "video_links")
    private List<String> videoLinks;

    @Column(name = "team_id")
    private Long teamId;

    public Player(PlayerRequestDTO requestDTO, Scouter scouter) {
        this.dni = requestDTO.getDni();
        this.surname = requestDTO.getSurname();
        this.name = requestDTO.getName();
        this.contact = requestDTO.getContact();
        this.category = requestDTO.getCategory();
        this.ubication = requestDTO.getUbication();
        this.height = requestDTO.getHeight();
        this.skillLeg = requestDTO.getSkillLeg();
        this.urlImage = requestDTO.getUrlImage();
        this.firstPosition = requestDTO.getFirstPosition();
        this.secondPosition = requestDTO.getSecondPosition();
        this.valoration = requestDTO.getValoration();
        this.characteristics = requestDTO.getCharacteristics();
        this.scouter = scouter;
        this.dateSeen = requestDTO.getDateSeen();
        this.divisionSeen = requestDTO.getDivisionSeen();
        this.teamSeen = requestDTO.getTeamSeen();
        this.campSeen = requestDTO.getCampSeen();
        this.contactTeamSeen = requestDTO.getContactTeamSeen();
        this.status = requestDTO.getStatus();
        this.videoLinks = new ArrayList<String>();
        this.teamId = requestDTO.getTeamId();
    }

    public void addVideoLink(String link) {
        if(!link.isEmpty() && link != null) {
            this.videoLinks.add(link);
        }
    }
}
