package com.microservice.calendar.controllers;

import com.microservice.calendar.http.ResponseApi;
import com.microservice.calendar.model.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.model.dtos.events.response.EventCalendarResponseDTO;
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
    public ResponseApi<List<EventCalendarResponseDTO>> getAllEvents(@PathVariable Long teamId) {
        return this.calendarService.getAllbyTeamId(teamId);
    }

    @GetMapping("/events/{eventId}")
    public ResponseApi<EventCalendarResponseDTO> getEventById(@PathVariable Long eventId) {
        return this.calendarService.getById(eventId);
    }

    @PostMapping("/events")
    public ResponseApi<?> createEvent(@RequestBody @Valid EventCalendarRequestDTO requestDTO) {
        return this.calendarService.createEvent(requestDTO);
    }

    @PutMapping("/events/{eventId}")
    public ResponseApi<?> updateEvent(@RequestBody @Valid EventCalendarRequestDTO requestDTO, @PathVariable Long eventId) {
        return this.calendarService.updateEvent(requestDTO,eventId);
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseApi<?> deleteEvent(@PathVariable Long eventId) {
        return this.calendarService.deleteEvent(eventId);
    }

}
