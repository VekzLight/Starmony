package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicalElementsResponseDTO implements Serializable {
    Boolean isEmpty;
    HashMap<Long, Long[]> commonChords;
    HashMap<Long, Long[]> commonIntervals;
    HashMap<Long, Long[]> commonProgressions;
    HashMap<Long, Long[]> commonScale;
}
