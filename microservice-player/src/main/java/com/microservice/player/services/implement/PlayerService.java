package com.microservice.player.services.implement;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.clinic_report.response.ClinicReportResponseDTO;
import com.microservice.player.model.dtos.player.request.PlayerMultimediaDTO;
import com.microservice.player.model.dtos.player.request.PlayerRequestDTO;
import com.microservice.player.model.dtos.player.response.PlayerResponseDTO;
import com.microservice.player.model.dtos.player.response.PlayerSearchDTO;
import com.microservice.player.model.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.model.dtos.stat.response.StatResponseDTO;
import com.microservice.player.model.entities.Player;
import com.microservice.player.model.entities.Scouter;
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
    public ResponseApi<List<PlayerSearchDTO>> getAllByTeamId(Long teamId) {
        List<PlayerSearchDTO> playerList = this.playerRepository.findAllByTeamId(teamId)
                .stream()
                .map(player -> new PlayerSearchDTO(
                        player.getDni(),
                        player.getSurname(),
                        player.getName(),
                        player.getCategory(),
                        player.getStatus(),
                        player.getScouter()
                )).collect(Collectors.toList());

        return new ResponseApi<>(playerList, HttpStatus.OK,"OK");
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseApi<PlayerResponseDTO> getByFullName(String fullName) {

        //check if the full name format is correct
        String[] parts = fullName.split("_");
        if (parts.length != 2) {
            return new ResponseApi<>(null,HttpStatus.BAD_REQUEST,"error, Invalid format");
        }

        String name = parts[0];
        String surname = parts[1];

        //get player
        Player player = this.playerRepository.findByNameAndSurname(name,surname);
        if(player != null) {
            //get stats player
            List<StatResponseDTO> statsDTO = this.statRepository.findAllByPlayerId(player.getDni())
                    .stream()
                    .map(stat -> new StatResponseDTO(stat))
                    .collect(Collectors.toList());
            //get clinic history player
            List<ClinicReportResponseDTO> clinicHistoryDTO = this.clinicReportRepository.findAllByPlayerId(player.getDni())
                    .stream()
                    .map(clinicReport -> new ClinicReportResponseDTO(clinicReport))
                    .collect(Collectors.toList());

            return new ResponseApi<>(
                    new PlayerResponseDTO(player,statsDTO,clinicHistoryDTO),
                    HttpStatus.OK,
                    "OK"
            );
        }
        throw new NotFoundException("Player","ID",player.getDni().toString());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseApi<PlayerResponseDTO> getById(Long dni) {
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

            return new ResponseApi<>(
                    new PlayerResponseDTO(player,statsDTO,clinicHistoryDTO),
                    HttpStatus.OK,
                    "OK"
            );
        }
        throw new NotFoundException("Player","ID",dni.toString());
    }

    @Override
    @Transactional
    public ResponseApi<?> create(PlayerRequestDTO player) {
        if (this.playerRepository.findByDni(player.getDni()) == null) {
            Optional<Scouter> scouter = this.scouterRepository.findById(player.getScouter().getId());
            if(!scouter.isEmpty()) {
                try {
                    Player newPlayer = new Player(player,scouter.get());
                    this.playerRepository.save(newPlayer);
                    return new ResponseApi<>(true,HttpStatus.CREATED,"OK");
                }
                catch (Exception ex) {
                    throw new ConflictPersistException("save","Player","ID",player.getDni().toString(),ex.getMessage());
                }
            }
            throw new NotFoundException("Scouter","ID",player.getScouter().getId());
        }
        throw new ConflictExistException("Player","ID",player.getDni().toString());
    }

    @Override
    @Transactional
    public ResponseApi<?> update(PlayerRequestDTO player, Long id) {
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
                return new ResponseApi<>(true, HttpStatus.ACCEPTED,"OK");
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Player","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseApi<?> updateMultimedia(PlayerMultimediaDTO requestDTO, Long id) {
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
                return new ResponseApi<>(true,HttpStatus.ACCEPTED,"OK");
            }
            catch (Exception ex) {
                throw new ConflictPersistException("updateMultimedia","Player","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseApi<?> delete(Long id) {
        Player player = this.playerRepository.findByDni(id);
        if(player != null) {
            try {
                this.playerRepository.delete(player);
                return new ResponseApi<>(true, HttpStatus.OK,"OK");
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","Player","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",id.toString());
    }

    /* ----- Scouter Context ----- */

    @Override
    @Transactional
    public boolean createScouter(@Valid ScouterRequestDTO scouter) {
        if(!this.scouterRepository.existsById(scouter.getId())) {
            try {
                this.scouterRepository.save(new Scouter(scouter));
                return true;
            }
            catch (Exception ex) {
                throw new ConflictPersistException("create","Scouter","ID",scouter.getId(), ex.getMessage());
            }
        }
        throw new ConflictExistException("Scouter","ID",scouter.getId());
    }

    @Override
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
                throw new ConflictPersistException("update","Scouter","ID",scouter.getId(), ex.getMessage());
            }
        }
        throw new NotFoundException("Scouter","ID",scouter.getId());
    }

    @Override
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
                throw new ConflictPersistException("delete","Scouter","ID",
                        requestDTO.getId(), ex.getMessage());
            }
        }
        throw new NotFoundException("Scouter","ID",requestDTO.getId());
    }

}
