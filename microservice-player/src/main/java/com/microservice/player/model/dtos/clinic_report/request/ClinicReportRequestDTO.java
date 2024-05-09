package com.microservice.player.model.dtos.clinic_report.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicReportRequestDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "title cannot be null")
    @NotEmpty(message = "title cannot be empty")
    private String title;

    @NotNull(message = "dateRegister cannot be null")
    private Date dateRegister;

    @NotNull(message = "report cannot be null")
    @NotEmpty(message = "report cannot be empty")
    private String report;

    @NotNull(message = "playerId cannot be null")
    private Long playerId;

}
