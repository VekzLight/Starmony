package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteChordMEDTO {
    private Long tonic;
    private List<Long> chord;
    private List<Long> intervals;
    private List<Long> notes;
}
