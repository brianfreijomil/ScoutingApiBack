package com.microservice.calendar.services.implement;

import com.microservice.calendar.dtos.events.request.EventCalendarRequestDTO;
import com.microservice.calendar.dtos.events.response.EventCalendarResponseDTO;
import com.microservice.calendar.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.calendar.dtos.scouter.response.ScouterResponseDTO;
import com.microservice.calendar.entities.EventCalendar;
import com.microservice.calendar.entities.Scouter;
import com.microservice.calendar.exceptions.ConflictExistException;
import com.microservice.calendar.exceptions.ConflictPersistException;
import com.microservice.calendar.exceptions.NotFoundException;
import com.microservice.calendar.repositories.EventCalendarRepository;
import com.microservice.calendar.repositories.ScouterRepository;
import com.microservice.calendar.services.interfaces.ICalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService implements ICalendarService {

    @Autowired
    private EventCalendarRepository eventCalendarRepository;
    @Autowired
    private ScouterRepository scouterRepository;

    /* ----- Events Context ----- */

    @Override
    @Transactional(readOnly = true)
    public List<EventCalendarResponseDTO> getAllbyTeamId(Long teamId) {
        return this.eventCalendarRepository.findAllByTeamId(teamId)
                .stream()
                .map(event -> new EventCalendarResponseDTO(
                        event,
                        event.getScouters()
                                .stream()
                                .map(scouter -> new ScouterResponseDTO(
                                        scouter.getId(),
                                        scouter.getSurname(),
                                        scouter.getName()
                                ))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventCalendarResponseDTO getById(Long eventId) {
        Optional<EventCalendar> event = this.eventCalendarRepository.findById(eventId);
        if(!event.isEmpty()) {
            return new EventCalendarResponseDTO(
                    event.get(),
                    event.get().getScouters()
                            .stream()
                            .map(scouter -> new ScouterResponseDTO(
                                    scouter.getId(),scouter.getSurname(),
                                    scouter.getName()))
                            .collect(Collectors.toList()));
        }
        throw new NotFoundException("EventCalendar","ID",eventId.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createEvent(EventCalendarRequestDTO event) {
        try {
            this.eventCalendarRepository.save(new EventCalendar(event));
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
                for (Scouter scouter : eventExisting.get().getScouters()) {
                    if(!this.scouterRepository.existsById(scouter.getId())) {
                        throw new NotFoundException("Scouter","ID",scouter.getId().toString());
                    }
                }
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
    public ResponseEntity<?> createScouter(ScouterRequestDTO scouter) {
        if(!this.scouterRepository.existsById(scouter.getId())) {
            try {
                this.scouterRepository.save(new Scouter(scouter));
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("create","Scouter","ID",scouter.getId().toString(), ex.getMessage());
            }
        }
        throw new ConflictExistException("Scouter","ID",scouter.getId().toString());
    }

    @Transactional
    public ResponseEntity<?> updateScouter(ScouterRequestDTO scouter, Long scouterId) {
        Optional<Scouter> scouterExisting = this.scouterRepository.findById(scouterId);
        if(!scouterExisting.isEmpty()) {
            try {
                scouterExisting.get().setSurname(scouter.getSurname());
                scouterExisting.get().setName(scouter.getName());
                //the id cannot be editable
                this.scouterRepository.save(scouterExisting.get());
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Scouter","ID",scouterId.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Scouter","ID",scouterId.toString());
    }

    @Transactional
    public ResponseEntity<?> deleteScouter(Long scouterId) {
        /*
         * ANTES DE ELIMINAR EL SCOUTER (REFERENCIA A UN USUARIO) DEBO REEMPLAZAR
         * SU REFERENCIA EN LOS PLAYER QUE LA CONTENGAN
         * */
        Optional<Scouter> scouterExisting = this.scouterRepository.findById(scouterId);
        if(!scouterExisting.isEmpty()) {
            try {
                this.scouterRepository.delete(scouterExisting.get());
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","Scouter","ID",scouterId.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("Scouter","ID",scouterId.toString());
    }
}
