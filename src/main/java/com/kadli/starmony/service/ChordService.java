package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ScaleGradesDTO;
import com.kadli.starmony.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ChordService extends CustomCrudService<Chord, Long>, DtoConversions<Chord, ChordDTO>{


    // Generadores
    Long getIdScaleGrade(Scale scale);
    HashMap<Long, List<ScaleGrade>> generateGradesOfScale(Scale scale);
    HashMap<Long, List<ScaleGrade>> generateGradesOfScaleAndSave(Scale scale);
    void generateAllGradesOfScaleAndSave();


    // Conversores
    ScaleGradesDTO scaleGradesToScaleGradeDTO(List<ScaleGrade> scaleGrades );

    List<Chord> getChordsWithIntervals(List<Interval> intervals);

    List<ScaleGrade> getGradesOfScale(Scale scale);

    Optional<ScaleGradesDTO> scaleGradeToScaleGradeDTO(ScaleGrade grade);

    List<Long> getAllIdsScaleGrades();

    List<ScaleGrade> getScaleGradeById(Long id);
}
