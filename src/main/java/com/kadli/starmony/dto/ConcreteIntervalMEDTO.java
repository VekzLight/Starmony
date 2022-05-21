package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteIntervalMEDTO {
    private Long tonic;
    private List<Long> intervals;
    private List<Long> notes;
}
