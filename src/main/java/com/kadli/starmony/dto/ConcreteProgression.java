package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteProgression extends ProgressionDTO implements Serializable {
    private List<ChordDTO> chords;
    private List<String> tonality;
}
