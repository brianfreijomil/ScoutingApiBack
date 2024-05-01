package com.microservice.calendar.model.dtos.events.response;

import com.microservice.calendar.model.entities.EventCalendar;
import com.microservice.calendar.model.entities.Scouter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCalendarResponseDTO {

    private Long id;
    private String title;
    private Timestamp dateInit;
    private Timestamp dateEnd;
    private String description;
    private List<Scouter> scouters;

    public EventCalendarResponseDTO(EventCalendar event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.dateInit = event.getDateInit();
        this.dateEnd = event.getDateEnd();
        this.description = event.getDescription();
        this.scouters = event.getScouters();
    }
}
