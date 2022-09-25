package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.entity.ScaleGrade;
import com.kadli.starmony.entity.ScaleGradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScaleGradeRepository extends JpaRepository<ScaleGrade, ScaleGradeId> {

    @Query("SELECT max(sg.id.id_scale_grade) FROM ScaleGrade sg")
    Long getMaxId();

    @Query("SELECT DISTINCT sg.id.id_scale_grade FROM ScaleGrade sg INNER JOIN sg.scaleOfChord s WHERE s.id = :idScale")
    Long getIdScaleGrade(@Param("idScale") Long idScale);

    @Query("FROM ScaleGrade sg INNER JOIN sg.scaleOfChord s WHERE s.id = :#{#scale.id}")
    List<ScaleGrade> getGradesOfScale(@Param("scale") Scale scale);

    @Query("SELECT DISTINCT sg.id.id_scale_grade FROM ScaleGrade sg")
    List<Long> getAllIds();

    @Query("FROM ScaleGrade sg WHERE sg.id.id_scale_grade = :idScaleGrade")
    List<ScaleGrade> getScaleGradeById(@Param("idScaleGrade") Long id);
}
