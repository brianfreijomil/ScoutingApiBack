package com.microservice.calendar.model.events_kafka;

import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventKafka {

    private String action; //delete,update,create,etc...
    private ScouterRequestDTO entity; //scouter create/update

}
