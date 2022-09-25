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
public class ScaleAnalizedDTO implements Serializable {
    boolean exist = true;


    // Used
    List<Long> concreteProgressionsIds;

    // Structure
    List<Long> concreteChordsIds;
    List<Long> concreteIntervalsIds;
}
