package com.microservice.player.repositories;

import com.microservice.player.entities.ClinicReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicReportRepository extends JpaRepository<ClinicReport,Long> {

    @Query("select c from ClinicReport c where c.player.dni =:playerId")
    List<ClinicReport> findAllByPlayerId(@Param("playerId") Long playerId);
}
