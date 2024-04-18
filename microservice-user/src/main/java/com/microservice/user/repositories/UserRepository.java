package com.microservice.user.repositories;

import com.microservice.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.teamId =:teamId")
    List<User> findAllByTeamId(@Param("teamId") Long teamId);

    @Query("select u from User u where u.username =:username")
    User findByUsername(@Param("username") String username);

    @Query("update User u set u.subscriptionStatus =:status where u.teamId =:teamId")
    void updateUserSubscriptionStatusByTeamId(@Param("status") Boolean status, @Param("teamId") Long teamId);

}
