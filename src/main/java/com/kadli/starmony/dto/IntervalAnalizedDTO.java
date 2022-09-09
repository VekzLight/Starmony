package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntervalAnalizedDTO implements Serializable {
    boolean exist = true;

    //Used
    HashMap<Long, Integer> concreteChordsIds;
    HashMap<Long, Integer> concreteScalesIds;

}

