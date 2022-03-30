package com.kadli.starmony.service;

import com.kadli.starmony.dto.ScaleGradeDTO;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.entity.ScaleGrade;

import java.util.List;

public interface ScaleGradesService {

    List<ScaleGrade> generateGradesOfScale(Scale scale);
    /**
     * Convierte todas las entidades en DTO's
     * @param scaleGrade
     * @return
     */
    ScaleGradeDTO toDTO(ScaleGrade scaleGrade);
}
