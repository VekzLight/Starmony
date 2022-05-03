package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntervalDTO implements Serializable {
    private Long id;
    private String name;
    private String symbol;
    private int semitones;
}
