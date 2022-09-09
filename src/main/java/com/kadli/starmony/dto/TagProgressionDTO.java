package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagProgressionDTO implements Serializable {
    private TagDTO tagDTO;
    private ProgressionDTO progressionDTO;
}
