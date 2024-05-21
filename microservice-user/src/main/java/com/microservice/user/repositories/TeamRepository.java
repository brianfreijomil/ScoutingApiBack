package com.microservice.user.repositories;

import com.microservice.user.model.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("select t from Team t where t.email =:email")
    Team findByEmail(@Param("email") String email);
}
