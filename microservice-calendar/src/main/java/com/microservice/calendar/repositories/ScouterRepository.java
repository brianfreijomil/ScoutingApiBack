package com.microservice.calendar.repositories;

import com.microservice.calendar.model.entities.Scouter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScouterRepository extends JpaRepository<Scouter,String> {
}
