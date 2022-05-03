package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recognizer")
@CrossOrigin(origins = "http://localhost:4200")
public class RecognizerController {


    // Elementos Musicales abstractos
    @Autowired
    private NoteService noteService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ProgressionService progressionService;




    // Elementos Musicales concretos
    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;






    // Notas
    @GetMapping("/note")
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<Note> notes = noteService.getAll();
        if (notes.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(notes.stream().map(noteService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.getById(id);
        if (!note.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(noteService.entityToDTO(note.get()), HttpStatus.OK);
    }

    @PostMapping(path = "/note/find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<NoteDTO>> findNote(@RequestBody NoteDTO noteDTO) {
        Note note = noteService.dtotoEntity(noteDTO);
        Optional<Note> response = noteService.get(note);
        if (!response.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(noteService.entityToDTO(response.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/note/findAll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> findAllNotes(@RequestBody NoteDTO noteDTO) {
        Note note = noteService.dtotoEntity(noteDTO);
        List<Note> response = noteService.getAll(note);
        if ( response.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( response.stream().map( noteService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(path = "/note/exist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existNote(@RequestBody NoteDTO noteDTO) {
        Note note = noteService.dtotoEntity(noteDTO);
        return new ResponseEntity<>(noteService.exist(note), HttpStatus.OK);
    }






    // Escalas
    @GetMapping("/scale")
    public ResponseEntity<List<ScaleDTO>> getAllScales() {
        List<Scale> scales = scaleService.getAll();
        if (scales.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(scales.stream().map(scaleService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/scale/{id}")
    public ResponseEntity<Optional<ScaleDTO>> getScaleById(@PathVariable Long id) {
        Optional<Scale> scale = scaleService.getById(id);
        if (!scale.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(scaleService.entityToDTO(scale.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/scale/find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ScaleDTO>> findScale(@RequestBody ScaleDTO scaleDTO) {
        Scale scale = scaleService.dtotoEntity(scaleDTO);
        Optional<Scale> response = scaleService.get(scale);
        if (!response.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(scaleService.entityToDTO(response.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/scale/findAll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScaleDTO>> findAllScale(@RequestBody ScaleDTO scaleDTO) {
        Scale scale = scaleService.dtotoEntity(scaleDTO);
        List<Scale> response = scaleService.getAll(scale);
        if (response.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity( response.stream().map(scaleService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(path = "/scale/exist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existScale(@RequestBody ScaleDTO scaleDTO) {
        Scale response = scaleService.dtotoEntity(scaleDTO);
        return new ResponseEntity<>(scaleService.exist(response), HttpStatus.OK);
    }




    // Escala concreta
    @GetMapping("/scale/concrete")
    public ResponseEntity<List<ConcreteScaleDTO>> getAllConcreteScales() {
        List<ConcreteScale> concreteScales = concreteScaleService.getAll();
        if (concreteScales.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        List<ConcreteScaleDTO> response = new ArrayList<>();
        List<ConcreteScale> concreteScalesSub = new ArrayList<>();
        Long currentId = 1L;
        for (ConcreteScale concreteScale : concreteScales) {
            Long itId = concreteScale.getId().getId_concrete_scale();
            if (itId != currentId) {
                response.add(concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScalesSub));
                currentId = itId;
                concreteScalesSub.clear();
            }
            concreteScalesSub.add(concreteScale);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/scale/concrete/{id}")
    public ResponseEntity<Optional<ConcreteScaleDTO>> getConcreteScaleById(@PathVariable Long id) {
        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById(id);
        if (concreteScales.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales)), HttpStatus.OK);
    }

    @GetMapping("/scale/concrete/s/{idScale}/t/{idTonic}")
    public ResponseEntity<Optional<ConcreteScaleDTO>> getConcreteScaleByScaleIdAndTonicId(@PathVariable Long idScale, @PathVariable Long idTonic) {
        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleWithTonic(idScale, idTonic);
        if (concreteScales.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales)), HttpStatus.OK);
    }





    // Acordes
    @GetMapping("/chord")
    public ResponseEntity<List<ChordDTO>> getAllChords() {
        List<Chord> chords = chordService.getAll();
        if (chords.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(chords.stream().map(chordService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/chord/{id}")
    public ResponseEntity<Optional<ChordDTO>> getChordById(@PathVariable Long id) {
        Optional<Chord> chord = chordService.getById(id);
        if (!chord.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(chordService.entityToDTO(chord.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/chord/find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ChordDTO>> findChord(@RequestBody ChordDTO chordDTO) {
        Chord chord = chordService.dtotoEntity(chordDTO);
        Optional<Chord> response = chordService.get(chord);
        if (!response.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(chordService.entityToDTO(response.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/chord/findAll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChordDTO>> findAllChords(@RequestBody ChordDTO chordDTO) {
        Chord chord = chordService.dtotoEntity(chordDTO);
        List<Chord> response = chordService.getAll(chord);
        if (response.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response.stream().map(chordService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(path = "/chord/exist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existChord(@RequestBody ChordDTO chordDTO) {
        Chord response = chordService.dtotoEntity(chordDTO);
        return new ResponseEntity<>(chordService.exist(response), HttpStatus.OK);
    }





    // Acordes Concretos
    @GetMapping("/chord/concrete")
    public ResponseEntity<List<ConcreteChordDTO>> getAllConcreteChords() {
        List<ConcreteChord> concreteChords = concreteChordService.getAllConcreteChords();
        if (concreteChords.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        List<ConcreteChordDTO> chordDTOS = new ArrayList<>();
        List<ConcreteChord> concreteChordsIt = new ArrayList<>();
        Long idCurrent = 1L;
        for (ConcreteChord it : concreteChords) {
            Long idIt = it.getCc_id().getId_concrete_chord();
            if (idIt != idCurrent) {
                idCurrent = idIt;
                concreteChordService.concreteChordToConcreteChordDTO(concreteChordsIt).ifPresent( chordDTO -> {
                    chordDTOS.add(chordDTO);
                } );
                concreteChordsIt.clear();
                concreteChordsIt.add(it);
            } else concreteChordsIt.add(it);
        }
        concreteChordService.concreteChordToConcreteChordDTO(concreteChordsIt).ifPresent( chordDTO -> {
            chordDTOS.add(chordDTO);
        } );

        return new ResponseEntity<>(chordDTOS, HttpStatus.OK);
    }

    @GetMapping("/chord/concrete/{id}")
    public ResponseEntity<Optional<ConcreteChordDTO>> getConcreteChordById(@PathVariable Long id) {
        List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordById(id);
        if (concreteChords.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(concreteChordService.concreteChordToConcreteChordDTO(concreteChords), HttpStatus.OK);
    }

    @GetMapping("/chord/concrete/c/{idChord}/t/{idTonic}")
    public ResponseEntity<Optional<ConcreteChordDTO>> getConcreteChordByChordIdAndTonicId(@PathVariable Long idChord, @PathVariable Long idTonic) {
        List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordWithTonic(idChord, idTonic);
        if (concreteChords.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(concreteChordService.concreteChordToConcreteChordDTO(concreteChords), HttpStatus.OK);
    }






    // Intervalos
    @GetMapping("/interval")
    public ResponseEntity<List<IntervalDTO>> getIntervals() {
        List<Interval> intervals = intervalService.getAll();
        if (intervals.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(intervals.stream().map(intervalService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/interval/{id}")
    public ResponseEntity<Optional<IntervalDTO>> getIntervalById(@PathVariable Long id) {
        Optional<Interval> interval = intervalService.getById(id);
        if (!interval.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(intervalService.entityToDTO(interval.get())), HttpStatus.OK);
    }

    @GetMapping("/interval/attribute/{attribute}/{value}")
    public ResponseEntity<Optional<IntervalDTO>> getIntervalById(@PathVariable String attribute, @PathVariable String value) {
        Optional<Interval> interval = intervalService.getByAttribute(attribute, value);
        if (!interval.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(intervalService.entityToDTO(interval.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/interval/find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<IntervalDTO>> findInterval(@RequestBody IntervalDTO intervalDTO) {
        Interval interval = intervalService.dtotoEntity(intervalDTO);
        Optional<Interval> response = intervalService.get(interval);
        if (!response.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(intervalService.entityToDTO(response.get())), HttpStatus.OK);
    }


    @PostMapping(path = "/interval/findAll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> findAllIntervals(@RequestBody IntervalDTO intervalDTO) {
        Interval interval = intervalService.dtotoEntity(intervalDTO);
        List<Interval> response = intervalService.getAll(interval);
        if (response.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response.stream().map(intervalService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }


    @PostMapping(path = "/interval/exist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existInterval(@RequestBody IntervalDTO intervalDTO) {
        Interval response = intervalService.dtotoEntity(intervalDTO);
        return new ResponseEntity<>(intervalService.exist(response), HttpStatus.OK);
    }





    // Intervalos Concretos
    @GetMapping("/interval/concrete")
    public ResponseEntity<List<ConcreteIntervalDTO>> getAllConcreteIntervals() {
        List<ConcreteInterval> concreteIntervals = concreteIntervalService.getAllConcreteIntervals();
        if (concreteIntervals.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(concreteIntervalService.concreteIntervalsToConcreteIntervalDTOS(concreteIntervals), HttpStatus.OK);
    }


    @GetMapping("/interval/concrete/{id}")
    public ResponseEntity<Optional<ConcreteIntervalDTO>> getConcreteIntervalById(@PathVariable Long id) {
        Optional<ConcreteInterval> concreteInterval = concreteIntervalService.getConcreteIntervalById(id);
        if (!concreteInterval.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(concreteIntervalService.concreteIntervalToConcreteIntervalDTO(concreteInterval.get())), HttpStatus.OK);
    }

    @GetMapping("/interval/concrete/i/{idInterval}/t/{idTonic}")
    public ResponseEntity<Optional<ConcreteIntervalDTO>> getConcreteIntervaldByIntervalIdAndTonicId(@PathVariable Long idInterval, @PathVariable Long idTonic) {
        Optional<ConcreteInterval> concreteInterval = concreteIntervalService.getConcreteIntervalWithTonic(idInterval, idTonic);
        if (!concreteInterval.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(concreteIntervalService.concreteIntervalToConcreteIntervalDTO(concreteInterval.get())), HttpStatus.OK);
    }






    // Progresiones Armonicas
    @GetMapping("/progression")
    public ResponseEntity<List<ProgressionDTO>> getAllProgressions() {
        List<Progression> progressions = progressionService.getAll();
        if (progressions.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(progressions.stream().map(progressionService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/progression/{id}")
    public ResponseEntity<Optional<ProgressionDTO>> getProgressionById(@PathVariable Long id) {
        Optional<Progression> progression = progressionService.getById(id);
        if (!progression.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(progressionService.entityToDTO(progression.get())), HttpStatus.OK);
    }


    @PostMapping(path = "/progression/find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ProgressionDTO>> findProgression(@RequestBody ProgressionDTO progressionDTO){
        Progression progression = progressionService.dtotoEntity(progressionDTO);
        Optional<Progression> response = progressionService.get( progression );
        if( !response.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( Optional.of(progressionService.entityToDTO(response.get())), HttpStatus.OK );
    }

    @PostMapping(path = "/progression/findAll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProgressionDTO>> findAllProgressions(@RequestBody ProgressionDTO progressionDTO){
        Progression progression = progressionService.dtotoEntity(progressionDTO);
        List<Progression> response = progressionService.getAll( progression );
        if( response.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( response.stream().map(progressionService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK );
    }

    @PostMapping(path = "/progression/exist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existProgression(@RequestBody ProgressionDTO progressionDTO){
        Progression response = progressionService.dtotoEntity(progressionDTO);
        return new ResponseEntity<>( progressionService.exist(response), HttpStatus.OK );
    }

}
