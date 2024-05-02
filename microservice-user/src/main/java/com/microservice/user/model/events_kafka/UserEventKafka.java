package com.microservice.user.model.events_kafka;

import com.microservice.user.model.dtos.user.ScouterDTO;
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
    private ScouterDTO entity; //scouter create/update

}
