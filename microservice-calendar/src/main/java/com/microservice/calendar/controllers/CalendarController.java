package com.microservice.calendar.controllers;

import com.microservice.calendar.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.dtos.events.response.EventCalendarResponseDTO;
import com.microservice.calendar.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.calendar.services.interfaces.ICalendarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private ICalendarService calendarService;

    /* ----- Events Context ----- */

    @GetMapping("/events/{teamid}")
    public List<EventCalendarResponseDTO> getAllEvents(@PathVariable Long teamId) {
        return this.calendarService.getAllbyTeamId(teamId);
    }

    @GetMapping("/events/{eventId}")
    public EventCalendarResponseDTO getEventById(@PathVariable Long eventId) {
        return this.calendarService.getById(eventId);
    }

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventCalendarRequestDTO requestDTO) {
        return this.calendarService.createEvent(requestDTO);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<?> updateEvent(@RequestBody @Valid EventCalendarRequestDTO requestDTO, @PathVariable Long eventId) {
        return this.calendarService.updateEvent(requestDTO,eventId);
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        return this.calendarService.deleteEvent(eventId);
    }

    /* ----- Scouter Context ----- */

    @PostMapping("/scouters")
    public ResponseEntity<?> createScouter(@RequestBody @Valid ScouterRequestDTO scouter) {
        return this.calendarService.createScouter(scouter);
    }

    @PutMapping("/scouters/{scouterId}")
    public ResponseEntity<?> updateScouter(@RequestBody @Valid ScouterRequestDTO scouter, @PathVariable Long scouterId) {
        return this.calendarService.updateScouter(scouter,scouterId);
    }

    @DeleteMapping("/scouters/{scouterId}")
    public ResponseEntity<?> deleteScouter(@PathVariable Long scouterId) {
        return this.calendarService.deleteScouter(scouterId);
    }


}
