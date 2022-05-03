package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteProgressionDTO;
import com.kadli.starmony.entity.*;

import java.util.List;
import java.util.Optional;

public interface ConcreteProgressionService {

    List<ConcreteProgression> generateConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades);
    List<ConcreteProgression> generateAndSaveConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades);
    List<ConcreteProgression> generateAndSaveAllConcreteProgressions();

    Long getMaxId();

    Optional<ConcreteProgressionDTO> concreteProgressionToConcreteProgressionDTO(List<ConcreteProgression> concreteProgressions);
}
