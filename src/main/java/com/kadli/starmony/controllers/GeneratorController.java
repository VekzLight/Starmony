package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.ProgressionDTO;
import com.kadli.starmony.dto.ScaleGradeDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/generator")
@CrossOrigin(origins = "http://localhost:4200")
public class GeneratorController {

    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ScaleGradesService scaleGradesService;

    @Autowired
    private ProgressionService progressionService;

    /**
     * Genera los grado de de una escala
     * @param scale
     * @return
     */
    @PostMapping("/chord/gradesOfScale")
    List<ScaleGradeDTO> generateGradesOfScale(@RequestBody Scale scale){
        System.out.println(scale.toString());
        return scaleGradesService.generateGradesOfScale(scale).stream().map(scaleGradesService::toDTO).collect(Collectors.toList());
    }

    /**
     * Genera los grados de una escala
     * @param id
     * @return
     */
    List<Chord> generateGradesOfScaleById(Long id){
        return null;
    }

    /**
     * Genera los grados de una progresion
     * @param progression
     * @return
     */
    List<Chord> generateGradesOfProgression(ProgressionDTO progression){
        return null;
    }

    /**
     * Genera los grados de una progresion
     * @param id
     * @return
     */
    List<Chord> generateGradesOfProgressionById(Long id){
        return null;
    }

}
