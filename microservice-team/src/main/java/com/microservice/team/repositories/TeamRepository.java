package com.microservice.team.repositories;

import com.microservice.team.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("select t from Team t where t.email =:email")
    boolean existsByEmail(@Param("email") String email);
}
