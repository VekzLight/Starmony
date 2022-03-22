package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Progression;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.service.ChordService;
import com.kadli.starmony.service.IntervalService;
import com.kadli.starmony.service.ProgressionService;
import com.kadli.starmony.service.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recognizer")
@CrossOrigin(origins = "http://localhost:4200")
public class RecognizerController {

    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ProgressionService progressionService;

    @GetMapping("/chord/getChords")
    public ResponseEntity<List<Chord>> getChords(){
        return new ResponseEntity<>(chordService.getChords(), HttpStatus.OK);
    }

    @GetMapping("/chord/getChords/{id}")
    public ResponseEntity<Optional<Chord>> getChords(@PathVariable Long id){
        return new ResponseEntity<>(chordService.getChord(id), HttpStatus.OK);
    }

    @GetMapping("/interval/getIntervals")
    public ResponseEntity<List<Interval>> getIntervals(){
        return new ResponseEntity<>(intervalService.getIntervals(), HttpStatus.OK);
    }

    @GetMapping("/interval/getInterval/{id}")
    public ResponseEntity<Optional<Interval>> getInterval(@PathVariable Long id){
        return new ResponseEntity<>(intervalService.getInterval(id), HttpStatus.OK);
    }

    @GetMapping("/scale/getScales")
    public ResponseEntity<List<Scale>> getScales(){
        return new ResponseEntity<>(scaleService.getScales(), HttpStatus.OK);
    }

    @GetMapping("/scale/getScale/{id}")
    public ResponseEntity<Optional<Scale>> getScale(@PathVariable Long id){
        return new ResponseEntity<>(scaleService.getScale(id), HttpStatus.OK);
    }

    @GetMapping("/progression/getProgressions")
    public ResponseEntity<List<Progression>> getProgressions(){
        return new ResponseEntity<>(progressionService.getProgressions(), HttpStatus.OK);
    }

    @GetMapping("/progression/getProgression/{id}")
    public ResponseEntity<Optional<Progression>> getProgression(@PathVariable Long id){
        return new ResponseEntity<>(progressionService.getProgresion(id), HttpStatus.OK);
    }
}
