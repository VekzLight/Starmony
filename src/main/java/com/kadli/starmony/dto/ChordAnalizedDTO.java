package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChordAnalizedDTO implements Serializable {
    boolean exist = true;

    // Uses
    HashMap<Long, Integer> concreteScalesIds;
    HashMap<Long, List<Integer>> concreteProgressionsIds;

    // Estructure
    List<Long> concreteIntervalsIds;
}
