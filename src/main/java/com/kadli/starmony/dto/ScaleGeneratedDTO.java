package com.kadli.starmony.dto;

import com.kadli.starmony.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleGeneratedDTO {
    private ScaleDTO scale;
    private HashMap<Long, ConcreteScaleDTO> concreteScales;
    private List<ScaleInterval> scaleIntervals;
    private HashMap<Long, ScaleGradesDTO> scaleGrades;
    private HashMap<Long, ConcreteScaleGradesDTO> concreteScaleGrades;
    private HashMap<Long, ProgressionGradeDTO> progressionGrades;
    private HashMap<Long, ConcreteProgressionDTO> concreteProgressions;
    private boolean saved;
}
