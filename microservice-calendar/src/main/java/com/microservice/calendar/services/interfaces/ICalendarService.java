package com.microservice.calendar.services.interfaces;

import com.microservice.calendar.model.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.model.dtos.events.response.EventCalendarResponseDTO;
import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICalendarService {

    ResponseEntity<List<EventCalendarResponseDTO>> getAllbyTeamId(Long teamId);

    ResponseEntity<EventCalendarResponseDTO> getById(Long eventId);

    ResponseEntity<?> createEvent(EventCalendarRequestDTO event);

    ResponseEntity<?> updateEvent(EventCalendarRequestDTO event, Long eventId);

    ResponseEntity<?> deleteEvent(Long eventId);

    boolean createScouter(ScouterRequestDTO scouter);

    boolean updateScouter(ScouterRequestDTO scouter);

    boolean deleteScouter(ScouterRequestDTO scouter);

}
