package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteScaleGradesDTO extends ScaleGradesDTO {
    private Long idConcreteScaleGrade;
    private HashMap<String, ConcreteChordDTO> concreteGrades;
}