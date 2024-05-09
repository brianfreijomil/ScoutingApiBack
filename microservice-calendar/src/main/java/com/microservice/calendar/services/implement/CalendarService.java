package com.microservice.calendar.services.implement;

import com.microservice.calendar.model.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.model.dtos.events.response.EventCalendarResponseDTO;
import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.calendar.model.entities.EventCalendar;
import com.microservice.calendar.model.entities.Scouter;
import com.microservice.calendar.exceptions.ConflictExistException;
import com.microservice.calendar.exceptions.ConflictPersistException;
import com.microservice.calendar.exceptions.NotFoundException;
import com.microservice.calendar.repositories.EventCalendarRepository;
import com.microservice.calendar.repositories.ScouterRepository;
import com.microservice.calendar.services.interfaces.ICalendarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService implements ICalendarService {

    @Autowired
    private EventCalendarRepository eventCalendarRepository;
    @Autowired
    private ScouterRepository scouterRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<EventCalendarResponseDTO>> getAllbyTeamId(Long teamId) {
        List<EventCalendarResponseDTO> eventsList = this.eventCalendarRepository.findAllByTeamId(teamId)
                .stream()
                .map(event -> new EventCalendarResponseDTO(event))
                .collect(Collectors.toList());

        return new ResponseEntity<>(eventsList, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<EventCalendarResponseDTO> getById(Long eventId) {
        Optional<EventCalendar> event = this.eventCalendarRepository.findById(eventId);
        if(!event.isEmpty()) {
            return new ResponseEntity<>(new EventCalendarResponseDTO(event.get()), HttpStatus.OK);
        }
        throw new NotFoundException("EventCalendar","ID",eventId.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createEvent(EventCalendarRequestDTO event) {
        try {
            EventCalendar newEventCalendar = new EventCalendar(event);

            List<Scouter> scoutersExisting = new ArrayList<>();

            for (ScouterRequestDTO s:event.getScouters()) {
                Optional<Scouter> scouter = this.scouterRepository.findById(s.getId());
                if(!scouter.isEmpty())
                    newEventCalendar.addScouterToEvent(scouter.get());
            }
            this.eventCalendarRepository.save(newEventCalendar);
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }
        catch (Exception ex) {
            throw new ConflictPersistException("create","EventCalendar","title", event.getTitle(), ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateEvent(EventCalendarRequestDTO event, Long eventId) {
        Optional<EventCalendar> eventExisting = this.eventCalendarRepository.findById(eventId);
        if(!eventExisting.isEmpty()) {
            try {
                //verifico si algun scouter no existe
                for (ScouterRequestDTO scouter : event.getScouters()) {
                    if(!this.scouterRepository.existsById(scouter.getId())) {
                        throw new NotFoundException("Scouter","ID",scouter.getId().toString());
                    }
                }
                eventExisting.get().setScouters(
                        event.getScouters()
                        .stream()
                        .map(s -> new Scouter(s.getId(),s.getSurname(),s.getName()))
                        .collect(Collectors.toList()));
                eventExisting.get().setTitle(event.getTitle());
                eventExisting.get().setDateInit(event.getDateInit());
                eventExisting.get().setDateEnd(event.getDateEnd());
                eventExisting.get().setDescription(event.getDescription());
                //the id cannot be edited
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","EventCalendar","ID",eventId.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("EventCalendar","ID",eventId.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteEvent(Long eventId) {
        Optional<EventCalendar> eventExisting = this.eventCalendarRepository.findById(eventId);
        if(!eventExisting.isEmpty()) {
            try {
                //los scouters asociados no deben ser eliminados
                this.eventCalendarRepository.delete(eventExisting.get());
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","EventCalendar","ID",eventId.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("EventCalendar","ID",eventId.toString());
    }

    /* ----- Scouter Context ----- */
    @Transactional
    public boolean createScouter(@Valid ScouterRequestDTO scouter) {
        if(!this.scouterRepository.existsById(scouter.getId())) {
            try {
                this.scouterRepository.save(
                        new Scouter(scouter.getId(), scouter.getSurname(), scouter.getName()));
                return true;
            }
            catch (Exception ex) {
                throw new ConflictPersistException("create","Scouter","ID",scouter.getId().toString(), ex.getMessage());
            }
        }
        throw new ConflictExistException("Scouter","ID",scouter.getId().toString());
    }

    @Transactional
    public boolean updateScouter(@Valid ScouterRequestDTO scouter) {
        Optional<Scouter> scouterExisting = this.scouterRepository.findById(scouter.getId());
        if(!scouterExisting.isEmpty()) {
            try {
                scouterExisting.get().setSurname(scouter.getSurname());
                scouterExisting.get().setName(scouter.getName());
                //the id cannot be editable
                this.scouterRepository.save(scouterExisting.get());
                return true;
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Scouter","ID",scouter.getId().toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Scouter","ID",scouter.getId().toString());
    }

    @Transactional
    public boolean deleteScouter(@Valid ScouterRequestDTO requestDTO) {
        /*
         * ANTES DE ELIMINAR EL SCOUTER (REFERENCIA A UN USUARIO) DEBO REEMPLAZAR
         * SU REFERENCIA EN LOS PLAYER QUE LA CONTENGAN
         * */
        Optional<Scouter> scouterExisting = this.scouterRepository.findById(requestDTO.getId());
        if(!scouterExisting.isEmpty()) {
            try {
                this.scouterRepository.delete(scouterExisting.get());
                return true;
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","Scouter","ID",requestDTO.getId().toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Scouter","ID",requestDTO.getId().toString());
    }
}
