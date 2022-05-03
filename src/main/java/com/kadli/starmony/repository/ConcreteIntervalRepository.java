package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConcreteIntervalRepository extends JpaRepository<ConcreteInterval, Long> {

    @Query("FROM ConcreteInterval ci INNER JOIN ci.intervalOfNotes i INNER JOIN ci.firstNote t WHERE i.id = :idInterval AND t.id = :idTonic")
    Optional<ConcreteInterval> getConcreteIntervalWithTonic(@Param("idInterval") Long idInterval, @Param("idTonic") Long idTonic);

    @Query("FROM ConcreteInterval ci WHERE ci.id_concrete_interval = :id")
    Optional<ConcreteInterval> getConcreteIntervalById(@Param("id") Long id);

    @Query("SELECT max(ci.id.id_concrete_interval) FROM ConcreteInterval ci")
    Long getMaxId();

}
