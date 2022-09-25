package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicalElementsAnalized implements Serializable {
    Boolean isEmpty;
    Integer length_;
    List<Long> concreteChordIds;
    List<Long>concreteScaleIds;
    List<Long>concreteProgressionIds;
    List<Long>concreteIntervalIds;
}
