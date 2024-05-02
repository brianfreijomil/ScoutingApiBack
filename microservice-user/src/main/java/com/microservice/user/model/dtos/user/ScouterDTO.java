package com.microservice.user.model.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ScouterDTO {

    private String id;
    private String surname;
    private String name;

}
