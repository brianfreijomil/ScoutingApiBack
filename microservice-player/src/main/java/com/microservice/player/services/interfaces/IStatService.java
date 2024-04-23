package com.microservice.player.services.interfaces;

import com.microservice.player.model.dtos.stat.request.StatRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IStatService {

    ResponseEntity<?> create(StatRequestDTO stat);

    ResponseEntity<?> update(StatRequestDTO stat, Long id);

    ResponseEntity<?> delete(Long id);
}
