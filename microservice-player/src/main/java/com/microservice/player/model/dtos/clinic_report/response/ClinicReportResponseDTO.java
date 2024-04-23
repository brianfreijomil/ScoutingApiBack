package com.microservice.player.model.dtos.clinic_report.response;

import com.microservice.player.model.entities.ClinicReport;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class ClinicReportResponseDTO {

    private Long id;
    private String title;
    private Date dateRegister;
    private String report;
    private Long playerId;

    public ClinicReportResponseDTO(ClinicReport clinicReport) {
        this.id = clinicReport.getId();
        this.title = clinicReport.getTitle();
        this.dateRegister = clinicReport.getDateRegister();
        this.report = clinicReport.getReport();
        this.playerId = clinicReport.getPlayer().getDni();
    }
}
