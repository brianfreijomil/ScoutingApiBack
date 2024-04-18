package com.microservice.player.services.interfaces;

import com.microservice.player.dtos.stat.request.StatRequestDTO;
import com.microservice.player.entities.Stat;
import org.springframework.http.ResponseEntity;

public interface IStatService {

    ResponseEntity<?> create(StatRequestDTO stat);

    ResponseEntity<?> update(StatRequestDTO stat, Long id);

    ResponseEntity<?> delete(Long id);
}
