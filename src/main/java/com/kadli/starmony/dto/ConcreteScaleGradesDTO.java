package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteScaleGradesDTO extends ScaleGradesDTO implements Serializable {
    private Long idConcreteScaleGrade;
    private HashMap<Integer, ConcreteChordDTO> concreteGrades;
}