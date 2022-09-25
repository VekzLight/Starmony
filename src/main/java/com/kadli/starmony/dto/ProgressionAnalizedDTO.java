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
public class ProgressionAnalizedDTO implements Serializable {
    boolean exist = true;


    // Structure
    List<Long> concreteScalesIds;
    HashMap<Long, Integer> concreteChordsIds;

}
