package com.microservice.calendar.model.entities;

import com.microservice.calendar.model.dtos.events.request.EventCalendarRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
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
    private Date dateInit;
    @Column
    private Date dateEnd;
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
    }

    public void addScouterToEvent(Scouter scouter) {
        if(scouter != null) {
            this.scouters.add(scouter);
        }
    }
}