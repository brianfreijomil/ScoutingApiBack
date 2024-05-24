package com.microservice.player.services.interfaces;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.clinic_report.request.ClinicReportRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IClinicReportService {

    ResponseApi<?> create(ClinicReportRequestDTO clinicReport);

    ResponseApi<?> update(ClinicReportRequestDTO clinicReport, Long id);

    ResponseApi<?> delete(Long id);
}
