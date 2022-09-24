package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressionGeneratedDTO implements Serializable {
    private ProgressionDTO progression;
    private List<ScaleDTO> scales;
    private HashMap<Long, ProgressionGradeDTO> progressionGrades;
    private HashMap<Long, ConcreteProgressionDTO> concreteProgressions;
    private boolean saved;
}
