package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteScaleMEDTO {
    private Long tonic;
    private List<Long> chords;
    private List<Long> scales;
    private List<Long> notes;
}
