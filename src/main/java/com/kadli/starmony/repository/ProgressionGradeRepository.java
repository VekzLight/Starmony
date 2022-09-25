package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ProgressionGrade;
import com.kadli.starmony.entity.ProgressionGradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressionGradeRepository extends JpaRepository<ProgressionGrade, ProgressionGradeId> {

    @Query("SELECT MAX(pg.id.id_progression_grade) FROM ProgressionGrade pg")
    Long getLastId();

    @Query("FROM ProgressionGrade pg where pg.id.id_progression_grade = :idProgressionGrade")
    List<ProgressionGrade> getProgressionGradesById(@Param("idProgressionGrade") Long idProgressionGrade);

    @Query("FROM ProgressionGrade pg INNER JOIN pg.progressionOfProgressionGrade p INNER JOIN pg.scaleGradeOfProgression sg where p.id = :idProgression and sg.id.id_scale_grade = :idScaleGrade")
    List<ProgressionGrade> getProgressionGradesByScaleGrade(@Param("idProgression") Long idProgression,@Param("idScaleGrade") Long idScaleGrade);

    @Query("SELECT pg.id.id_progression_grade FROM ProgressionGrade pg INNER JOIN pg.progressionOfProgressionGrade p INNER JOIN pg.scaleGradeOfProgression sg where p.id = :idProgression and sg.id.id_scale_grade = :idScaleGrade")
    Long getIdProgressionGradeByProgressionAndSG(@Param("idProgression") Long idProgression,@Param("idScaleGrade") Long idScaleGrade);
}