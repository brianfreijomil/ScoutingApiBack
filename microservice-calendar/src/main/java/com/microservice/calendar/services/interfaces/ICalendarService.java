package com.microservice.calendar.services.interfaces;

import com.microservice.calendar.http.ResponseApi;
import com.microservice.calendar.model.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.model.dtos.events.response.EventCalendarResponseDTO;
import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICalendarService {

    ResponseApi<List<EventCalendarResponseDTO>> getAllbyTeamId(Long teamId);

    ResponseApi<EventCalendarResponseDTO> getById(Long eventId);

    ResponseApi<?> createEvent(EventCalendarRequestDTO event);

    ResponseApi<?> updateEvent(EventCalendarRequestDTO event, Long eventId);

    ResponseApi<?> deleteEvent(Long eventId);

    boolean createScouter(ScouterRequestDTO scouter);

    boolean updateScouter(ScouterRequestDTO scouter);

    boolean deleteScouter(ScouterRequestDTO scouter);

}
