package com.microservice.player.dtos.player.response;

import com.microservice.player.dtos.clinic_report.response.ClinicReportResponseDTO;
import com.microservice.player.dtos.stat.response.StatResponseDTO;
import com.microservice.player.entities.ClinicReport;
import com.microservice.player.entities.Player;
import com.microservice.player.entities.Scouter;
import com.microservice.player.entities.Stat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PlayerResponseDTO {

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
    private Scouter scouter;
    private Date dateSeen;
    private String divisionSeen;
    private String teamSeen;
    private String campSeen;
    private String contactTeamSeen;
    private Boolean status;
    //@Column
    //private List<String> videoLinks;
    private Long teamId;
    //all stats ord by date
    private List<StatResponseDTO> stats;
    //all clinic reports ord by date
    private List<ClinicReportResponseDTO> clinicHistory;

    public PlayerResponseDTO(Player player, List<StatResponseDTO> stats, List<ClinicReportResponseDTO> clinicHistory) {
        this.dni = player.getDni();
        this.surname = player.getSurname();
        this.name = player.getName();
        this.category = player.getCategory();
        this.ubication = player.getUbication();
        this.height = player.getHeight();
        this.skillLeg = player.getSkillLeg();
        this.urlImage = player.getUrlImage();
        this.firstPosition = player.getFirstPosition();
        this.secondPosition = player.getSecondPosition();
        this.valoration = player.getValoration();
        this.characteristics = player.getCharacteristics();
        this.scouter = player.getScouter();
        this.dateSeen = player.getDateSeen();
        this.divisionSeen = player.getDivisionSeen();
        this.teamSeen = player.getTeamSeen();
        this.campSeen = player.getCampSeen();
        this.contactTeamSeen = player.getContactTeamSeen();
        this.status = player.getStatus();
        this.teamId = player.getTeamId();
        this.stats = stats;
        this.clinicHistory = clinicHistory;
    }
}
