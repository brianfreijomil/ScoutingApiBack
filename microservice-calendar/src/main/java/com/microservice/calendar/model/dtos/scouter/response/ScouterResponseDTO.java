package com.microservice.calendar.model.dtos.scouter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScouterResponseDTO {

    private Long id;
    private String surname;
    private String name;

}
