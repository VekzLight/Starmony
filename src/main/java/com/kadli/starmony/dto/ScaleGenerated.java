package com.kadli.starmony.dto;

import com.kadli.starmony.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleGenerated implements Serializable {
    private Scale scale;
    private HashMap<Long, List<ConcreteScale>> concreteScales;
    private List<ScaleInterval> scaleIntervals;
    private HashMap<Integer, List<ScaleGrade>> scaleGrades;
    private HashMap<String, List<ConcreteScaleGrade>> concreteScaleGrades;
    private HashMap<Long, List<ProgressionGrade>> progressionGrades;
    private HashMap<String, List<ConcreteProgression>> concreteProgressions;
}
