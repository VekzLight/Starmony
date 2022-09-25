package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagScaleDTO implements Serializable {
    private TagDTO tagDTO;
    private ScaleDTO scaleDTO;
}
