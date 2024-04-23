package com.microservice.user.services.implement;

import com.microservice.user.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventsService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void emitKafkaEvent(String topicName, Object event) {
        //recibo un nombre para el topic y el evento a emitir
        try {
            this.kafkaTemplate.send(topicName, JsonUtils.toJson(event));
        }
        catch (Exception ex) {
            //si hay un error sale por consola
            log.info("Error due to: {}",ex.getMessage());
        }
    }

}
