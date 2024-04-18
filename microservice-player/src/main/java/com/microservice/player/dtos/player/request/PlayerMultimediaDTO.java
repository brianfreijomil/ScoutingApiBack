package com.microservice.player.dtos.player.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerMultimediaDTO {

    //videoLinks can be null
    private List<String> videoLinks;

    //urlImage can be null
    private String urlImage;

}
