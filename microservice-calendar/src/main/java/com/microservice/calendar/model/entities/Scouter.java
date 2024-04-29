package com.microservice.calendar.model.entities;

import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
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
    private Long id;

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