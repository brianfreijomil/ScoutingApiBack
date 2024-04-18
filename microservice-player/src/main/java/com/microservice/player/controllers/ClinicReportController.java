package com.microservice.player.controllers;

import com.microservice.player.dtos.clinic_report.request.ClinicReportRequestDTO;
import com.microservice.player.entities.ClinicReport;
import com.microservice.player.entities.Stat;
import com.microservice.player.services.interfaces.IClinicReportService;
import com.microservice.player.services.interfaces.IStatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clinic_history")
public class ClinicReportController {

    @Autowired
    private IClinicReportService clinicReportService;

    @PostMapping("")
    public ResponseEntity<?> createClinicReport(@RequestBody @Valid ClinicReportRequestDTO clinicReport) {
        return this.clinicReportService.create(clinicReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClinicReport(@RequestBody @Valid ClinicReportRequestDTO clinicReport, @PathVariable Long id) {
        return this.clinicReportService.update(clinicReport,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClinicReport(@PathVariable Long id) {
        return this.clinicReportService.delete(id);
    }

}
