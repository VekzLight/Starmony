package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.service.ChordService;
import com.kadli.starmony.service.IntervalService;
import com.kadli.starmony.service.ProgressionService;
import com.kadli.starmony.service.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private ProgressionService progressionService;

}
