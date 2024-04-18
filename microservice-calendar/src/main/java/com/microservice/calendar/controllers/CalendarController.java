package com.microservice.calendar.controllers;

import com.microservice.calendar.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.calendar.services.interfaces.ICalendarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private ICalendarService calendarService;

    /* ----- Events Context ----- */

    //get events

    //get Event

    //create event

    //update event

    //delete event



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
