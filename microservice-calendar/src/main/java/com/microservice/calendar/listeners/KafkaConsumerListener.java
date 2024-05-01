package com.microservice.calendar.listeners;

import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
import com.microservice.calendar.services.interfaces.ICalendarService;
import com.microservice.calendar.model.events_kafka.UserEventKafka;
import com.microservice.calendar.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerListener {

    @Autowired
    private ICalendarService calendarService;

    //private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    //groupId grupos de consumidores, recibe uno solo, comparte con los demas
    //groupId = "first-group-id"
    @KafkaListener(topics = {"scouter-management-topic"}, groupId = "scouter-calendar-management")
    public void listener(String message) {

        UserEventKafka eventReceived = JsonUtils.fromJson(message, UserEventKafka.class);
        ScouterRequestDTO scouter = new ScouterRequestDTO(
                eventReceived.getEntity().getId(),
                eventReceived.getEntity().getSurname(),
                eventReceived.getEntity().getName());

        switch (eventReceived.getAction()) {
            case "create":
                this.calendarService.createScouter(scouter);
                break;
            case "update":
                this.calendarService.updateScouter(scouter);
                break;
            case "delete":
                this.calendarService.deleteScouter(scouter);
                break;
            default:
                log.error("Unknow operation: {}", eventReceived.getAction());
                break;
        }

        log.info(
                "a user arrived with id: {}, lastname: {}, y firstname: {}",
                scouter.getId(), scouter.getSurname(),scouter.getName()
        );
    }
}
