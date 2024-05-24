package com.microservice.player.repositories;

import com.microservice.player.model.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    List<Player> findAllByTeamId(Long teamId);

    @Query("select p from Player p where p.name =:name and p.surname =:surname")
    Player findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    Player findByDni(Long dni);
}
