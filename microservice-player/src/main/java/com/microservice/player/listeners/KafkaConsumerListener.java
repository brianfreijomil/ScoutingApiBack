package com.microservice.player.listeners;

import com.microservice.player.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.player.services.interfaces.IPlayerService;
import com.microservice.player.utils.JsonUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerListener {

    @Autowired
    private IPlayerService playerService;

    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    //groupId grupos de consumidores, recibe uno solo, comparte con los demas
    @KafkaListener(topics = {"first-topic-scouting-app"}, groupId = "first-group-id")
    public void listener(String message) {

        ScouterRequestDTO scouter = JsonUtils.fromJson(message, ScouterRequestDTO.class);
        //persist entity
        this.playerService.createScouter(scouter);

        log.info("Llego el scouter con el id: {}, apellido: {}, y nombre: {}", scouter.getId(), scouter.getSurname(),scouter.getName());
    }
}
