package com.microservice.player.repositories;

import com.microservice.player.model.entities.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Stat,Long> {

    @Query("select s from Stat s where s.player.dni =:playerId")
    List<Stat> findAllByPlayerId(@Param("playerId") Long playerId);
}
