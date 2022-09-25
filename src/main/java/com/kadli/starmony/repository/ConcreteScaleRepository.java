package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteScale;
import com.kadli.starmony.entity.ConcreteScaleId;
import com.kadli.starmony.entity.Scale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConcreteScaleRepository extends JpaRepository<ConcreteScale, ConcreteScaleId> {

    @Query("FROM ConcreteScale cs INNER JOIN cs.scaleOfNotes s INNER JOIN cs.notesOfScale n WHERE s.id = :idScale AND n.id = :idNote")
    Optional<ConcreteScale> getConcreteScale(@Param("idScale") Long idScale, @Param("idNote") Long idNote);

    @Query("FROM ConcreteScale cs WHERE cs.id.id_concrete_scale = :idConcreteScale")
    List<ConcreteScale> getCompleteConcreteScale(@Param("idConcreteScale") Long idConcreteScale);

    @Query("FROM ConcreteScale cs INNER JOIN cs.scaleOfNotes s WHERE s.id = :idScale")
    List<ConcreteScale> getConcreteScalesByScale(@Param("idScale") Long id);

    @Query("SELECT cs.id.id_concrete_scale FROM ConcreteScale cs INNER JOIN cs.scaleOfNotes s INNER JOIN cs.notesOfScale n  WHERE s.id = :idScale AND n.id = :idTonic and cs.id.position = 1")
    Long getIdConcreteScale(@Param("idScale") Long idScale, @Param("idTonic") Long idTonic);

    @Query("SELECT max(cs.id.id_concrete_scale) FROM ConcreteScale cs")
    Long getMaxId();

    @Query("SELECT cs.id.id_concrete_scale FROM ConcreteScale cs INNER JOIN cs.notesOfScale n WHERE n.id = :idTonic AND cs.id.position = 1")
    List<Long> getIdConcreteScalesWithTonic(@Param("idTonic") Long idTonic);

    @Query("SELECT cs.id.id_concrete_scale FROM ConcreteScaleGrade csg INNER JOIN csg.concreteScale cs INNER JOIN csg.concreteChord cc WHERE cc.id.id_concrete_chord in (:idConcreteChords)")
    List<Long> getIdConcreteScaleWithConcreteChords(@Param("idConcreteChords") List<Long> idConcreteChords);

    @Query("SELECT DISTINCT cs.id.id_concrete_scale FROM ConcreteScale cs")
    List<Long> getAllIds();


    @Query("SELECT DISTINCT s" +
            " FROM ConcreteScale cs" +
            " INNER JOIN cs.scaleOfNotes s" +
            " WHERE cs.id.id_concrete_scale = :concreteScaleId")
    Scale getScaleFromConcreteScale(@Param("concreteScaleId")Long concreteScaleId);

    @Query("SELECT DISTINCT n.id" +
            " FROM ConcreteScale cs" +
            " INNER JOIN cs.notesOfScale n" +
            " WHERE cs.id.position = 1" +
            "   AND cs.id.id_concrete_scale = :idConcreteScale")
    Long getIdTonicOfConcreteScaleId(@Param("idConcreteScale") Long concreteScaleId);
}
