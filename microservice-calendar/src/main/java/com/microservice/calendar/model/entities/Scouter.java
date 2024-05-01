package com.microservice.calendar.model.entities;

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

}
