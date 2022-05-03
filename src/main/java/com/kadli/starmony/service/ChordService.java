package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ScaleGradesDTO;
import com.kadli.starmony.entity.*;

import java.util.List;
import java.util.Optional;

public interface ChordService extends CustomCrudService<Chord, Long>, DtoConversions<Chord, ChordDTO>{


    // Generadores
    Long getIdScaleGrade(Scale scale);
    List<ScaleGrade> generateGradesOfScale(Scale scale);
    List<ScaleGrade> generateGradesOfScaleAndSave(Scale scale);
    void generateAllGradesOfScaleAndSave();


    // Conversores
    ScaleGradesDTO scaleGradesToScaleGradeDTO(List<ScaleGrade> scaleGrades );

    List<Chord> getChordsWithIntervals(List<Interval> intervals);

    List<ScaleGrade> getGradesOfScale(Scale scale);

    Optional<ScaleGradesDTO> scaleGradeToScaleGradeDTO(ScaleGrade grade);
}
