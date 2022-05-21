package com.kadli.starmony.repository;

import com.kadli.starmony.entity.UserInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIntervalRepository extends JpaRepository<UserInterval, Long> {

    @Query("FROM UserInterval ui INNER JOIN ui.userOfConcreteIntervals u WHERE u.id = :idUser")
    List<UserInterval> getAllIntervalsOfUserId(@Param("idUser") Long id);

    @Query("SELECT MAX(ui.id_user_concrete_interval) FROM UserInterval ui")
    Long getLast();

    @Query("SELECT DISTINCT ui.id_user_concrete_interval FROM UserInterval ui INNER JOIN ui.concreteIntervalsOfUser ci INNER JOIN ui.userOfConcreteIntervals u WHERE u.id = :idUser AND ci.id_concrete_interval = :idConcreteInterval")
    Long getConcreteIdByIntervalId(@Param("idUser") Long idUser, @Param("idConcreteInterval") Long idConcreteInterval);

}
