package com.kadli.starmony.dto;

import com.kadli.starmony.entity.ConcreteChord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteProgressionDTO extends ProgressionGradeDTO implements Serializable {
    private Long id_concrete_progression;
    private Long id_concrete_scale;
    private HashMap<Integer, ConcreteChordDTO> concreteGrades;
}
