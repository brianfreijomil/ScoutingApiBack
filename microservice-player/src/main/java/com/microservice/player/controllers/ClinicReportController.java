package com.microservice.player.controllers;

import com.microservice.player.http.response.ResponseApi;
import com.microservice.player.model.dtos.clinic_report.request.ClinicReportRequestDTO;
import com.microservice.player.services.interfaces.IClinicReportService;
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
    public ResponseApi<?> createClinicReport(@RequestBody @Valid ClinicReportRequestDTO clinicReport) {
        return this.clinicReportService.create(clinicReport);
    }

    @PutMapping("/{id}")
    public ResponseApi<?> updateClinicReport(@RequestBody @Valid ClinicReportRequestDTO clinicReport, @PathVariable Long id) {
        return this.clinicReportService.update(clinicReport,id);
    }

    @DeleteMapping("/{id}")
    public ResponseApi<?> deleteClinicReport(@PathVariable Long id) {
        return this.clinicReportService.delete(id);
    }

}
