package com.microservice.calendar.repositories;

import com.microservice.calendar.entities.EventCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCalendarRepository extends JpaRepository<EventCalendar,Long> {

    @Query("select e from EventCalendar e where e.teamId =:teamId")
    List<EventCalendar> findAllByTeamId(@Param("teamId") Long teamId);

}
