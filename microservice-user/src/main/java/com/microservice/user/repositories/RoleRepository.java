package com.microservice.user.repositories;

import com.microservice.user.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("select r from Role r where r.roleName =:roleName")
    Role findByType(@Param("roleName") String roleName);

}
