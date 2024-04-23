package com.microservice.player.services.implement;

import com.microservice.player.dtos.clinic_report.response.ClinicReportResponseDTO;
import com.microservice.player.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.dtos.stat.response.StatResponseDTO;
import com.microservice.player.entities.ClinicReport;
import com.microservice.player.entities.Player;
import com.microservice.player.entities.Scouter;
import com.microservice.player.entities.Stat;
import com.microservice.player.exceptions.ConflictExistException;
import com.microservice.player.exceptions.ConflictPersistException;
import com.microservice.player.exceptions.NotFoundException;
import com.microservice.player.repositories.ClinicReportRepository;
import com.microservice.player.repositories.PlayerRepository;
import com.microservice.player.repositories.ScouterRepository;
import com.microservice.player.repositories.StatRepository;
import com.microservice.player.services.interfaces.IPlayerService;
import jakarta.validation.Valid;
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
public class PlayerService implements IPlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StatRepository statRepository;
    @Autowired
    private ClinicReportRepository clinicReportRepository;
    @Autowired
    private ScouterRepository scouterRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PlayerSearchDTO> getAllByTeamId(Long teamId) {
        return this.playerRepository.findAllByTeamId(teamId)
                .stream()
                .map(player -> new PlayerSearchDTO(
                        player.getDni(),
                        player.getSurname(),
                        player.getName(),
                        player.getCategory(),
                        player.getStatus(),
                        player.getScouter()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerResponseDTO getById(Long dni) {
        //get player
        Player player = this.playerRepository.findByDni(dni);
        if(player != null) {
            //get stats player
            List<StatResponseDTO> statsDTO = this.statRepository.findAllByPlayerId(dni)
                    .stream()
                    .map(stat -> new StatResponseDTO(stat))
                    .collect(Collectors.toList());
            //get clinic history player
            List<ClinicReportResponseDTO> clinicHistoryDTO = this.clinicReportRepository.findAllByPlayerId(dni)
                    .stream()
                    .map(clinicReport -> new ClinicReportResponseDTO(clinicReport))
                    .collect(Collectors.toList());

            return new PlayerResponseDTO(player,statsDTO,clinicHistoryDTO);
        }
        throw new NotFoundException("Player","ID",dni.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(PlayerRequestDTO player) {
        if (this.playerRepository.findByDni(player.getDni()) != null) {
            try {
                Player newPlayer = new Player(player);
                this.playerRepository.save(newPlayer);
                return new ResponseEntity<>(true,HttpStatus.CREATED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("save","Player","ID",player.getDni().toString(),ex.getMessage());
            }

        }
        throw new ConflictExistException("Player","ID",player.getDni().toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(PlayerRequestDTO player, Long id) {
        Player playerExisting = this.playerRepository.findByDni(id);
        if(playerExisting != null) {
            try {
                playerExisting.setDni(player.getDni());
                playerExisting.setSurname(player.getSurname());
                playerExisting.setName(player.getName());
                playerExisting.setContact(player.getContact());
                playerExisting.setUbication(player.getUbication());
                playerExisting.setCategory(player.getCategory());
                playerExisting.setHeight(player.getHeight());
                playerExisting.setSkillLeg(player.getSkillLeg());
                playerExisting.setUrlImage(player.getUrlImage());
                playerExisting.setFirstPosition(player.getFirstPosition());
                playerExisting.setSecondPosition(player.getSecondPosition());
                playerExisting.setValoration(player.getValoration());
                playerExisting.setCharacteristics(player.getCharacteristics());
                playerExisting.setDateSeen(player.getDateSeen());
                playerExisting.setDivisionSeen(player.getDivisionSeen());
                playerExisting.setTeamSeen(player.getTeamSeen());
                playerExisting.setCampSeen(player.getCampSeen());
                playerExisting.setContactTeamSeen(player.getContactTeamSeen());
                playerExisting.setStatus(player.getStatus());
                //scouter and teamId are not included

                this.playerRepository.save(playerExisting);
                return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Player","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateMultimedia(PlayerMultimediaDTO requestDTO, Long id) {
        Player playerExisting = this.playerRepository.findByDni(id);
        if(playerExisting != null) {
            try {
                //chequeo que el nuevo no sea null
                if(requestDTO.getUrlImage() != null && !requestDTO.getUrlImage().isEmpty()) {
                    playerExisting.setUrlImage(requestDTO.getUrlImage());
                }
                //chequeo que el nuevo no sea null
                if(requestDTO.getVideoLinks() != null) {
                    playerExisting.setVideoLinks(requestDTO.getVideoLinks());
                }
                this.playerRepository.save(playerExisting);
                return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("updateMultimedia","Player","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Player player = this.playerRepository.findByDni(id);
        if(player != null) {
            try {
                this.playerRepository.delete(player);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","Player","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",id.toString());
    }

    /* ----- Scouter Context ----- */
    @Transactional
    public ResponseEntity<?> createScouter(@Valid ScouterRequestDTO scouter) {
        if(!this.scouterRepository.existsById(scouter.getId())) {
            try {
                this.scouterRepository.save(new Scouter(scouter));
                return new ResponseEntity<>(true,HttpStatus.CREATED);
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
