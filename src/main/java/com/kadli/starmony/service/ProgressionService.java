package com.kadli.starmony.service;

import com.kadli.starmony.dto.ProgressionDTO;
import com.kadli.starmony.dto.ProgressionGradeDTO;
import com.kadli.starmony.entity.Progression;
import com.kadli.starmony.entity.ProgressionGrade;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.entity.ScaleGrade;

import java.util.List;
import java.util.Optional;

public interface ProgressionService extends CustomCrudService<Progression, Long>, DtoConversions<Progression, ProgressionDTO> {

    List<ProgressionGrade> generateProgressionGradeSimple(Progression progression, List<ScaleGrade> scaleGrades);
    List<ProgressionGrade> generateProgressionGradeForce(Progression progression, List<ScaleGrade> scaleGrades);

    List<ProgressionGrade> generateAndSaveProgressionGradeSimple(Progression progression, List<ScaleGrade> scaleGrades);
    List<ProgressionGrade> generateAndSaveAllProgresionGradeSimple();

    Optional<ProgressionGradeDTO> progressionGradeToProgressionGradeDTO(List<ProgressionGrade> progressionGrades);

    List<ProgressionGrade> getCompleteProgressionGradeByScaleGrade(Long idProgression, Long idScaleGrade);
    List<ProgressionGrade> getCompleteProgressionGradeById(Long idProgressionGrade);

    Long getLastId();

    Long getIdProgressionGradeByScaleGrade(Long idProgression, Long idScaleGrade);
}
