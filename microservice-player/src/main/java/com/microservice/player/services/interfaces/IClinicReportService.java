package com.microservice.player.services.interfaces;

import com.microservice.player.model.dtos.clinic_report.request.ClinicReportRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IClinicReportService {

    ResponseEntity<?> create(ClinicReportRequestDTO clinicReport);

    ResponseEntity<?> update(ClinicReportRequestDTO clinicReport, Long id);

    ResponseEntity<?> delete(Long id);
}
