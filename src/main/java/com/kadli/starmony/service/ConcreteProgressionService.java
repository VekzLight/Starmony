package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteProgressionDTO;
import com.kadli.starmony.entity.*;

import java.util.List;
import java.util.Optional;

public interface ConcreteProgressionService {

    List<ConcreteProgression> generateConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades, Long idConcreteScale);
    List<ConcreteProgression> generateAndSaveConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGradesLong, Long idConcreteScale);
    List<ConcreteProgression> generateAndSaveAllConcreteProgressions();

    Long getMaxId();

    Optional<ConcreteProgressionDTO> concreteProgressionToConcreteProgressionDTO(List<ConcreteProgression> concreteProgressions);

    List<ConcreteProgression> getCompleteConcreteProgressionById(Long idProgression);

    List<ConcreteProgressionDTO> getCompleteConcreteProgressionsByIdScaleAndChord(List<Long> idConcreteScales, List<Long> idConcreteChords, Long idTonic);

    List<ConcreteProgression> getCompleteConcreteProgressionsByProgressionGradeAndConcreteScale(Long idConcreteScale, Long idProgressionGrade);
}
