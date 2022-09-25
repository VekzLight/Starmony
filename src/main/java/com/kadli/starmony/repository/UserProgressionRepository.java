package com.kadli.starmony.repository;

import com.kadli.starmony.entity.UserProgression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserProgressionRepository extends JpaRepository<UserProgression, Long> {

    @Query("FROM UserProgression up INNER JOIN up.userOfConcreteProgressions u WHERE u.id = :idUser")
    List<UserProgression> getAllProgressionsOfUserId(@Param("idUser") Long id);

    @Query("SELECT MAX(up.id_user_concrete_progression) FROM UserProgression up")
    Long getLast();

    @Query("SELECT up.id_user_concrete_progression FROM UserProgression up INNER JOIN up.concreteProgressionsOfUser cp INNER JOIN up.userOfConcreteProgressions u WHERE u.id = :idUser AND cp.id.id_concrete_progression = :idConcreteProgression")
    Long getConcreteIdByProgressionId(@Param("idUser") Long idUser, @Param("idConcreteProgression") Long idConcreteProgression);

}
