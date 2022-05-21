package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteProgressionMEDTO {
    private Long idTonic;
    private List<Long> idNotes;
    private List<Long> idProgressions;
    private List<Long> idScales;
    private List<Long> idChords;
}
