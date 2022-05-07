package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analyzer")
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyzerController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ProgressionService progressionService;


    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;

    @Autowired
    private ConcreteScaleService concreteScaleService;


    // Intervals
    @PostMapping(path = "/interval/chord",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getIntervalsOfChord(@RequestBody ChordDTO chordDto){
        Chord chord = chordService.dtotoEntity(chordDto);
        if( !chordService.exist(chord) ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.getIntervalsOfChord(chord);
        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/concrete/chord/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervalsOfChordConcrete(@RequestBody ConcreteChordDTO concreteChordDTO){
        Optional<Chord> chord = chordService.get( Chord.builder().code(concreteChordDTO.getCode()).build() );
        Note tonic = noteService.dtotoEntity(concreteChordDTO.getTonic());

        if( !chord.isPresent() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        List<Interval> intervals = intervalService.getIntervalsOfChord(chord.get());

        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        for(Interval interval: intervals)
            concreteIntervals.add( concreteIntervalService.getConcreteIntervalWithTonic(interval.getId(), tonic.getId()).get() );


        if( concreteIntervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( concreteIntervals.stream().map( concreteIntervalService::concreteIntervalToConcreteIntervalDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/scale/tonic",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getIntervalsOfScale(@RequestBody ScaleDTO scaleDto){
        Scale scale = scaleService.dtotoEntity(scaleDto);
        if( !scaleService.exist(scale) ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.getIntervalsOfScaleByTonic( scale );
        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/scale/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getAllIntervalsOfScale(@RequestBody ScaleDTO scaleDto){
        Scale scale = scaleService.dtotoEntity(scaleDto);
        if( !scaleService.exist(scale) ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.getIntervalsOfScaleByAll( scale );
        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/note",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getIntervalsOfNotes(@RequestBody List<NoteDTO> notesDto){
        List<Note> notes = notesDto.stream().map( noteService::dtotoEntity).collect(Collectors.toList());
        List<Interval> intervals = intervalService.getIntervalsOfNotes( notes );

        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };





    // Chords
    @PostMapping(path = "/chord/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChordDTO>> getChordsWithIntervals(@RequestBody List<IntervalDTO> intervalsDto){
        List<Interval> intervals = intervalsDto.stream().map( intervalService::dtotoEntity ).collect(Collectors.toList());
        List<Chord> chords = chordService.getChordsWithIntervals(intervals);
        if( chords.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( chords.stream().map( chordService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/chord/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScaleGradesDTO> getGradesOfScale(@RequestBody ScaleDTO scaleDto){
        Scale scale = scaleService.dtotoEntity(scaleDto);
        List<ScaleGrade> grades = chordService.getGradesOfScale(scale);
        if( grades.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( chordService.scaleGradesToScaleGradeDTO(grades), HttpStatus.OK );
    };

    @PostMapping(path = "/chord/concrete/scale/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ConcreteScaleGradesDTO>> getConcreteGradesOfScale(@RequestBody ConcreteScaleDTO concreteScaleDTO){
        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( concreteScaleDTO.getIdConcrete() );
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        Optional<ConcreteScaleGradesDTO> concreteScaleGradeDTO = concreteChordService.concreteScaleGradesToConcreteScaleGradesDTO(concreteScaleGrades);
        if( !concreteScaleGradeDTO.isPresent() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteScaleGradeDTO, HttpStatus.OK );
    };






    // Scale
    @PostMapping(path = "/scale/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScaleDTO>> getScalesWithIntervals(@RequestBody List<IntervalDTO> intervalDTOS){
        List<Interval> intervals = intervalDTOS.stream().map( intervalService::dtotoEntity ).collect(Collectors.toList());
        List<Scale> scales = scaleService.getScalesWithIntervals(intervals);
        if( scales.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( scales.stream().map( scaleService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/scale/chord",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScaleDTO>> getScalesWithChord(@RequestBody List<ChordDTO> chordsDTO){
        List<Chord> chords = chordsDTO.stream().map(chordService::dtotoEntity).collect(Collectors.toList());
        List<Scale> scales = scaleService.getScalesWithChords(chords);
        if( scales.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( scales.stream().map( scaleService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };


    // Progression Grades
    @PostMapping(path = "/progression/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ProgressionGradeDTO>> getProgressionGradesOfScale(@RequestBody ProgressionScaleDTO progressionScaleDTO){
        Scale scale = scaleService.dtotoEntity(progressionScaleDTO.getScaleDTO());
        Progression progression = progressionService.dtotoEntity(progressionScaleDTO.getProgressionDTO());

        if( !scaleService.exist(scale) ) return new ResponseEntity( new Message(-1, "Escala no encotrada") , HttpStatus.NOT_FOUND);
        if( !progressionService.exist(progression) ) return new ResponseEntity( new Message(-1, "Progression encontrada") , HttpStatus.NOT_FOUND);

        Long idScaleGrade = chordService.getIdScaleGrade(scale);

        List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeByScaleGrade( progression.getId(), idScaleGrade );
        if( progressionGrades.isEmpty() ) return new ResponseEntity( new Message(-1, "No existen los grados de la progression") , HttpStatus.NOT_FOUND);

        Optional<ProgressionGradeDTO> progressionGradeDTO = progressionService.progressionGradeToProgressionGradeDTO(progressionGrades);


        return new ResponseEntity<>( progressionGradeDTO, HttpStatus.OK );
    };

}