package com.microservice.player.services.implement;

import com.microservice.player.dtos.stat.request.StatRequestDTO;
import com.microservice.player.entities.Player;
import com.microservice.player.entities.Stat;
import com.microservice.player.exceptions.ConflictPersistException;
import com.microservice.player.exceptions.NotFoundException;
import com.microservice.player.repositories.PlayerRepository;
import com.microservice.player.repositories.StatRepository;
import com.microservice.player.services.interfaces.IStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StatService implements IStatService {

    @Autowired
    private StatRepository statRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    @Transactional
    public ResponseEntity<?> create(StatRequestDTO stat) {
        Player player = this.playerRepository.findByDni(stat.getPlayerId());
        if(player != null) {
            try {
                this.statRepository.save(new Stat(stat,player));
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException(
                        "create",
                        "Stat",
                        "playerId",
                        stat.getPlayerId().toString(),
                        ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",stat.getPlayerId().toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(StatRequestDTO stat, Long id) {
        Optional<Stat> statExisting = this.statRepository.findById(id);
        if(!statExisting.isEmpty()) {
            try {
                statExisting.get().setDateRegister(stat.getDateRegister());
                statExisting.get().setMatches(stat.getMatches());
                statExisting.get().setMinutes(stat.getMinutes());
                statExisting.get().setScores(stat.getScores());
                statExisting.get().setAssists(stat.getAssists());
                statExisting.get().setYellowCards(stat.getYellowCards());
                statExisting.get().setRedCards(stat.getRedCards());
                //player id cannot be edited
                this.statRepository.save(statExisting.get());
                return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("update","Stat","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Stat","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Stat> statExisting = this.statRepository.findById(id);
        if(!statExisting.isEmpty()) {
            try {
                this.statRepository.delete(statExisting.get());
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","Stat","ID",id.toString(),ex.getMessage());
            }
        }
        throw new NotFoundException("Stat","ID",id.toString());
    }
}
