package com.microservice.player.repositories;

import com.microservice.player.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    List<Player> findAllByTeamId(Long teamId);

    Player findByDni(Long dni);
}
