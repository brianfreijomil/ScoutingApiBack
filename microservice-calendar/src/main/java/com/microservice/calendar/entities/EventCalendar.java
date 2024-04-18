package com.microservice.calendar.entities;

import com.microservice.calendar.dtos.events.request.EventCalendarRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class EventCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private Timestamp dateInit;
    @Column
    private Timestamp dateEnd;
    @Column(nullable = false)
    private String description;
    @Column
    private Long teamId;
    @ManyToMany
    private List<Scouter> scouters;

    public EventCalendar(EventCalendarRequestDTO requestDTO) {
        this.id = requestDTO.getId();
        this.title = requestDTO.getTitle();
        this.dateInit = requestDTO.getDateInit();
        this.dateEnd = requestDTO.getDateEnd();
        this.description = requestDTO.getDescription();
        this.teamId = requestDTO.getTeamId();
        this.scouters = requestDTO.getScouters();
    }

    public void addScouterToEvent(Scouter scouter) {
        if(scouter != null) {
            this.scouters.add(scouter);
        }
    }
}