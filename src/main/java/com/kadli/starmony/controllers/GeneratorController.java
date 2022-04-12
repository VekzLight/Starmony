package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.Message;
import com.kadli.starmony.dto.ProgressionDTO;
import com.kadli.starmony.dto.ScaleGradeDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import java.util.List;
import java.util.Optional;
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
    private NoteService noteService;

    @Autowired
    private ConcreteMusicalElementsService musicalElementsService;

    @Autowired
    private ProgressionService progressionService;


    @GetMapping("/chord/concrete/{idChord}/tonic/{idTonic}")
    List<ConcreteChord> generateConcreteChordWithTonic(@PathVariable Long idChord, @PathVariable Long idTonic){
        return musicalElementsService.generateConcreteChords(chordService.getById(idChord).get(), noteService.getById(idTonic).get() );
    }

    @GetMapping("/intervals/chord/{id}")
    ResponseEntity<List<Interval>> generateIntervalsOfChord(@PathVariable Long id){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(intervalService.generateIntervalsOfChord(chord.get()), HttpStatus.OK);
    }

    @GetMapping("/chords/concrete/save")
    ResponseEntity<List<ConcreteChord>> generateAllConcreteChords(){
        return new ResponseEntity(musicalElementsService.generateAndSaveAllConcretechords(), HttpStatus.OK);
    }

    @GetMapping("/intervals/concrete/save")
    ResponseEntity<List<ConcreteInterval>> generateAllConcreteIntervals(){
        return new ResponseEntity(musicalElementsService.generateAllConcreteIntervalsAndSave(), HttpStatus.OK);
    }

    @GetMapping("/chord/concrete/{idChord}/tonic/{idTonic}/save")
    void generateAndSaveConcreteChordWithTonic(@PathVariable Long idChord, @PathVariable Long idTonic){
        musicalElementsService.generateAndSaveConcreteChords(chordService.getById(idChord).get(), noteService.getById(idTonic).get() );
    }

    @GetMapping("/scale/concrete/save")
    void generateAndSaveAllConcreteScales(){
        musicalElementsService.generateAllConcreteScalesAndSave();
    }
}
