package com.kadli.starmony.repository;

import com.kadli.starmony.entity.UserScale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserScaleRepository extends JpaRepository<UserScale, Long> {

    @Query("FROM UserScale us INNER JOIN us.userOfConcreteScales u WHERE u.id = :idUser")
    List<UserScale> getAllScalesOfUserId(@Param("idUser") Long id);

    @Query("SELECT MAX(us.id_user_concrete_scale) FROM UserScale us")
    Long getLast();

    @Query("SELECT us.id_user_concrete_scale FROM UserScale us INNER JOIN us.concreteScalesOfUser cs INNER JOIN us.userOfConcreteScales u WHERE u.id = :idUser AND cs.id.id_concrete_scale = :idConcreteScales")
    Long getConcreteIdByScaleId(@Param("idUser") Long idUser, @Param("idConcreteScales") Long idConcreteScales);

}
