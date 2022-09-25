package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressionScaleDTO {
    private ProgressionDTO progressionDTO;
    private ScaleDTO scaleDTO;
}
