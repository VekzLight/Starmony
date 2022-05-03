package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressionGradeDTO extends ProgressionDTO implements Serializable {
    private ScaleDTO scale;

    private Long id_progression_grade;
    private HashMap<Integer, ChordDTO> grades;
}
