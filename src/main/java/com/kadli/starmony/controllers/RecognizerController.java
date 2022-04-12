package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
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
    private NoteService noteService;

    @Autowired
    private ProgressionService progressionService;

    @Autowired
    private ConcreteMusicalElementsService musicalElementsService;
    /*
    * TODO:
    *   + Obtener Todos los Elementos Musicales
    *   + Obtener los Elementos Musicales por sus atributo
    *   + Comprobar Existencia por sus atributo
    * */

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes(){
        List<Note> notes = noteService.getAll();
        if( notes.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(notes.stream().map(noteService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/chords")
    public ResponseEntity<List<ChordDTO>> getAllChords(){
        List<Chord> chords = chordService.getAll();
        if( chords.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(chords.stream().map(chordService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/chord/{id}")
    public ResponseEntity<ChordDTO> getChordById(@PathVariable Long id){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity( chordService.entityToDTO(chord.get()), HttpStatus.OK);
    }

    @GetMapping("/chord/{id}/{value}")
    public ResponseEntity<ChordDTO> getChordByAttribute(@PathVariable Long id, @PathVariable String value){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity( chordService.entityToDTO(chord.get()), HttpStatus.OK);
    }

    /*
    @GetMapping("/chords/concrete")
    public ResponseEntity<List<ConcreteChordDTO>> getAllConcreteChords(){
        List<ConcreteChord> chords = concreteChordService.getAll();
        if( chords.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(chords.stream().map(concreteChordService::toDtoConcrete).collect(Collectors.toList()), HttpStatus.OK);
    }
*/
    @GetMapping("/chord/concrete/{id}")
    public ResponseEntity<ConcreteChordDTO> getConcreteChordWithTonic(@PathVariable Long id){
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/chord/concrete/{idChord}/tonic/{idTonic}")
    public ResponseEntity<ConcreteChord> getConcreteChord(@PathVariable Long idChord, @PathVariable Long idTonic){
        Optional<Chord> chord = chordService.getById(idChord);
        Optional<Note> note = noteService.getById(idTonic);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(musicalElementsService.generateConcreteChords(chord.get(), note.get()), HttpStatus.OK);
    }


    @GetMapping("/intervals")
    public ResponseEntity<List<IntervalDTO>> getIntervals(){
        List<Interval> intervals = intervalService.getAll();
        if( intervals.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(intervals.stream().map(intervalService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    /*
    @GetMapping("/intervals/concrete")
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervals(){
        List<Interval> intervals = intervalService.getAll();
        if( intervals.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(intervals.stream().map(intervalService::entityToDTOConcrete).collect(Collectors.toList()), HttpStatus.OK);
    }
*/
    @GetMapping("/interval/{id}")
    public ResponseEntity<Optional<Interval>> getInterval(@PathVariable Long id){
        return new ResponseEntity<>(intervalService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/scales")
    public ResponseEntity<List<Scale>> getScales(){
        List<Scale> scales = scaleService.getAll();
        if( scales.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(scales.stream().map(scaleService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }
/*
    @GetMapping("/scales/concrete")
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScales(){
        List<Scale> scales = scaleService.getAll();
        if( scales.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity(scales.stream().map(scaleService::entityToDTOConcrete).collect(Collectors.toList()), HttpStatus.OK);
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

*/
}
