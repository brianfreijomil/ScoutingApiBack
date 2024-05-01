package com.microservice.player.repositories;

import com.microservice.player.model.entities.Scouter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScouterRepository extends JpaRepository<Scouter,String> {
}
