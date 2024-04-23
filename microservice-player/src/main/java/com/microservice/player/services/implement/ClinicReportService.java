package com.microservice.player.services.implement;

import com.microservice.player.model.dtos.clinic_report.request.ClinicReportRequestDTO;
import com.microservice.player.model.entities.ClinicReport;
import com.microservice.player.model.entities.Player;
import com.microservice.player.exceptions.ConflictPersistException;
import com.microservice.player.exceptions.NotFoundException;
import com.microservice.player.repositories.ClinicReportRepository;
import com.microservice.player.repositories.PlayerRepository;
import com.microservice.player.services.interfaces.IClinicReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClinicReportService implements IClinicReportService {

    @Autowired
    private ClinicReportRepository clinicReportRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    @Transactional
    public ResponseEntity<?> create(ClinicReportRequestDTO clinicReport) {
        Player player = this.playerRepository.findByDni(clinicReport.getPlayerId());
        if(player != null) {
            try {
                this.clinicReportRepository.save(new ClinicReport(clinicReport,player));
                return new ResponseEntity<>(true,HttpStatus.CREATED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException(
                        "create",
                        "ClinicReport",
                        "dateRegister",
                        clinicReport.getDateRegister().toString(),
                        ex.getMessage());
            }
        }
        throw new NotFoundException("Player","ID",clinicReport.getPlayerId().toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(ClinicReportRequestDTO clinicReport, Long id) {
        Optional<ClinicReport> clinicReportExisting = this.clinicReportRepository.findById(id);
        if(!clinicReportExisting.isEmpty()) {
            try {
                clinicReportExisting.get().setTitle(clinicReport.getTitle());
                clinicReportExisting.get().setDateRegister(clinicReport.getDateRegister());
                clinicReportExisting.get().setReport(clinicReport.getReport());
                //player id cannot be edited
                this.clinicReportRepository.save(clinicReportExisting.get());
                return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","ClinicReport","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("ClinicReport","ID",id.toString());
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<ClinicReport> clinicReportExisting = this.clinicReportRepository.findById(id);
        if(!clinicReportExisting.isEmpty()) {
            try {
                this.clinicReportRepository.delete(clinicReportExisting.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            catch (Exception ex) {
                throw new ConflictPersistException("delete","ClinicReport","ID",id.toString(), ex.getMessage());
            }
        }
        throw new NotFoundException("ClinicReport","ID",id.toString());
    }
}
