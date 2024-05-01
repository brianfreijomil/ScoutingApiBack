package com.microservice.user.services.implement;

import com.microservice.user.services.interfaces.IKafkaEventsService;
import com.microservice.user.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventsService implements IKafkaEventsService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void emitKafkaEvent(Object event) {

        String topicName = "scouter-management-topic";

        try {
            this.kafkaTemplate.send(topicName, JsonUtils.toJson(event));
        }
        catch (Exception ex) {
            log.info("Error : {}",ex.getMessage());
        }
    }

}
