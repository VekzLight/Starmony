package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntervalDTO {
    private Long id;
    private String name;
    private String symbol;
    private int semitones;
}
