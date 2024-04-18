package com.microservice.calendar.services.interfaces;

import com.microservice.calendar.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.dtos.events.response.EventCalendarResponseDTO;
import com.microservice.calendar.dtos.scouter.request.ScouterRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICalendarService {

    List<EventCalendarResponseDTO> getAllbyTeamId(Long teamId);

    EventCalendarResponseDTO getById(Long eventId);

    ResponseEntity<?> createEvent(EventCalendarRequestDTO event);

    ResponseEntity<?> updateEvent(EventCalendarRequestDTO event, Long eventId);

    ResponseEntity<?> deleteEvent(Long eventId);

    ResponseEntity<?> createScouter(ScouterRequestDTO scouter);

    ResponseEntity<?> updateScouter(ScouterRequestDTO scouter, Long scouterId);

    ResponseEntity<?> deleteScouter(Long scouterId);

}
