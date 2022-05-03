package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleGradesDTO extends ScaleDTO implements Serializable {
    private Long idScaleGrade;
    private HashMap<String, ChordDTO> grades;
}
