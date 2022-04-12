package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteInterval;
import com.kadli.starmony.entity.ConcreteIntervalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConcreteIntervalRepository extends JpaRepository<ConcreteInterval, ConcreteIntervalId> {

    @Query("FROM ConcreteInterval ci WHERE ci.id.id_interval = :idInterval AND ci.id.id_note1 = :idTonic AND ci.id.id_note2 = :idNote")
    Optional<ConcreteInterval> getConcreteInterval(@Param("idInterval") Long idInterval, @Param("idTonic") Long idTonic, @Param("idNote") Long idNote);

}
