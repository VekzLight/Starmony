package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteChord;
import com.kadli.starmony.entity.ConcreteScaleGrade;
import com.kadli.starmony.entity.ConcreteScaleGradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcreteScaleGradeRepository extends JpaRepository<ConcreteScaleGrade, ConcreteScaleGradeId> {

    @Query("SELECT MAX(csg.id.id_concrete_scale_grade) FROM ConcreteScaleGrade csg")
    Long getLastId();

    @Query("FROM ConcreteScaleGrade csg WHERE csg.id.id_concrete_scale = :idConcrete")
    List<ConcreteScaleGrade> getCompleteConcreteScaleGradeByCSGId(@Param("idConcrete") Long idConcrete);

    @Query("SELECT DISTINCT csg.id.id_concrete_scale_grade FROM ConcreteScaleGrade csg INNER JOIN csg.concreteScale cs INNER JOIN csg.concreteChord cc WHERE cc.id.id_concrete_chord = :idConcreteChord")
    List<Long> getConcreteScaleGradeIdWithConcreteChordId(@Param("idConcreteChord") Long concreteChordId);

    @Query("SELECT DISTINCT csg.id.id_concrete_scale_grade FROM ConcreteScaleGrade csg")
    List<Long> getAllConcreteScaleGradesIds();

    @Query("SELECT DISTINCT csg.id.id_concrete_scale_grade" +
            " FROM ConcreteScaleGrade csg" +
            " WHERE csg.id.id_concrete_scale = :idConcreteScale")
    Long getConcreteScaleGradeIdWithScaleAndTonicId(@Param("idConcreteScale") Long idConcreteScale);

    @Query("SELECT DISTINCT csg.id.id_concrete_chord" +
            " FROM ConcreteScaleGrade csg" +
            " WHERE csg.id.id_concrete_scale = :idConcreteScale")
    List<Long> getConcreteChordsOfConcreteScale(@Param("idConcreteScale") Long concreteScaleId);
}
