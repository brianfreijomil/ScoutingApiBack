package com.microservice.player.services.interfaces;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.stat.request.StatRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IStatService {

    ResponseApi<?> create(StatRequestDTO stat);

    ResponseApi<?> update(StatRequestDTO stat, Long id);

    ResponseApi<?> delete(Long id);
}
