package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteProgression;
import com.kadli.starmony.entity.ConcreteProgressionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcreteProgressionRepository extends JpaRepository<ConcreteProgression, ConcreteProgressionId> {

    @Query(" SELECT MAX( cp.id.id_concrete_progression ) FROM ConcreteProgression cp")
    Long getLastId();

    @Query("FROM ConcreteProgression cp WHERE cp.id.id_concrete_progression = :idProgression ")
    List<ConcreteProgression> getCompleteConcreteProgressionById(@Param("idProgression") Long idProgression);

    @Query("SELECT DISTINCT cp.id.id_concrete_progression FROM ConcreteProgression cp INNER JOIN cp.concreteChord cc INNER JOIN cp.concreteScale cs WHERE cc.cc_id.id_concrete_chord in (:idConcreteChords) OR cs.id.id_concrete_scale in (:idConcreteScales)")
    List<Long> getIdCompleteConcreteProgressionByIdScaleAndChord(@Param("idConcreteChords") List<Long> idConcreteChords, @Param("idConcreteScales") List<Long> idConcreteScales);

    @Query("FROM ConcreteProgression cp INNER JOIN cp.progressionGrade pg INNER JOIN cp.concreteScale cs WHERE pg.id.id_progression_grade = :idProgressionGrade AND cs.id.id_concrete_scale = :idConcreteScale")
    List<ConcreteProgression> getCompleteConcreteProgressionByPGAndCS(@Param("idConcreteScale") Long idConcreteScale, @Param("idProgressionGrade") Long idProgressionGrade);
}
