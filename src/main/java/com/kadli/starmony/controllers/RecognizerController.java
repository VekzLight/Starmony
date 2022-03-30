package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.Message;
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
import java.util.stream.Collectors;

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

    /*
    * TODO:
    *   + Obtener Todos los Elementos Musicales
    *   + Obtener los Elementos Musicales por sus atributo
    *   + Comprobar Existencia por sus atributo
    * */


    @GetMapping("/chords")
    public ResponseEntity<List<ChordDTO>> getChords(){
        List<Chord> chords = chordService.getAll();
        if( chords.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(chords.stream().map(chordService::toDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/chord/{id}")
    public ResponseEntity<ChordDTO> getChords(@PathVariable Long id){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity( chordService.toDTO(chord.get()), HttpStatus.OK);
    }

    @GetMapping("/chord/{id}")
    public ResponseEntity<ChordDTO> getChords(@PathVariable Long id, @PathVariable String value){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity( chordService.toDTO(chord.get()), HttpStatus.OK);
    }

    @GetMapping("/intervals")
    public ResponseEntity<List<Interval>> getIntervals(){
        List<Interval> intervals = intervalService.getAll();
        if( !intervals.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(intervals.stream().map(intervalService::toDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/interval/{id}")
    public ResponseEntity<Optional<Interval>> getInterval(@PathVariable Long id){
        return new ResponseEntity<>(intervalService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/scales")
    public ResponseEntity<List<Scale>> getScales(){
        return new ResponseEntity<>(scaleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/scale/{id}")
    public ResponseEntity<Optional<Scale>> getScale(@PathVariable Long id){
        return new ResponseEntity<>(scaleService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/progressions")
    public ResponseEntity<List<Progression>> getProgressions(){
        return new ResponseEntity<>(progressionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/progression/{id}")
    public ResponseEntity<Optional<Progression>> getProgression(@PathVariable Long id){
        return new ResponseEntity<>(progressionService.getById(id), HttpStatus.OK);
    }


}
