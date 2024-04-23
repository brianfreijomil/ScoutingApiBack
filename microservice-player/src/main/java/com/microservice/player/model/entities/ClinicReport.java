package com.microservice.player.model.entities;

import com.microservice.player.model.dtos.clinic_report.request.ClinicReportRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clinic_history")
public class ClinicReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date dateRegister;

    @Column(nullable = false)
    private String report;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    public ClinicReport(ClinicReportRequestDTO requestDTO, Player player) {
        this.title = requestDTO.getTitle();
        this.dateRegister = requestDTO.getDateRegister();
        this.report = requestDTO.getReport();
        this.player = player;
    }

}
