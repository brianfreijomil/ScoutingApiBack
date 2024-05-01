package com.microservice.player.model.entities;

import com.microservice.player.model.dtos.scouter.request.ScouterRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scouters")
public class Scouter {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    public Scouter(ScouterRequestDTO requestDTO) {
        this.id = requestDTO.getId();
        this.surname = requestDTO.getSurname();
        this.name = requestDTO.getName();
    }

}
