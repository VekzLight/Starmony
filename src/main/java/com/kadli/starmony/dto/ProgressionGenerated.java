package com.kadli.starmony.dto;

import com.kadli.starmony.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressionGenerated implements Serializable {
    private Progression progression;
    private List<Scale> scales;
    private HashMap<Long, List<ProgressionGrade>> progressionGrades;
    private HashMap<Long, List<ConcreteProgression>> concreteProgressions;

}
