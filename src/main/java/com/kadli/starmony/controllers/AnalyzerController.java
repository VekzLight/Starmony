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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"http://localhost:4200", "http://starmony.duckdns.org:2000"})
@RestController
@RequestMapping("/api/analyzer")
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

    @Autowired
    private ConcreteProgressionService concreteProgressionService;

    @Autowired
    private TagService tagService;


    @PostMapping(path = "/MusicalElements",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MusicalElementsResponseDTO> analizeME(@RequestBody MusicalElementsAnalized musicalElementsAnalized) {
        HashMap<Long, Long[]> commonIntervals = new HashMap<>();
        HashMap<Long, Long[]> commonChords = new HashMap<>();
        HashMap<Long, Long[]> commonScale = new HashMap<>();
        HashMap<Long, Long[]> commonProgressions = new HashMap<>();

        if(musicalElementsAnalized.getConcreteProgressionIds().size() != 0){
            if(musicalElementsAnalized.getConcreteChordIds().size() > 2){

            } else {

            }
        }

        if(musicalElementsAnalized.getConcreteChordIds().size() != 0){
            if(musicalElementsAnalized.getConcreteChordIds().size() > 2){

            } else {

            }
        }

        return new ResponseEntity<>( null, HttpStatus.OK);
    }

    // TAGS
    @GetMapping("/tag")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        if (tags.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tags.stream().map(tagService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/tag/{idTag}")
    public ResponseEntity<TagDTO> getAllTags(@PathVariable Long idTag) {
        Optional<Tag> tag = tagService.getTagById(idTag);
        if (!tag.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tagService.entityToDTO(tag.get()), HttpStatus.OK);
    }

    @GetMapping("/tag/scale")
    public ResponseEntity<List<TagScaleDTO>> getAllTagsScale() {
        List<TagScale> tagScales = tagService.getAllTagsScale();
        if (tagScales.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tagScales.stream().map(tagScale -> tagService.entityToTagScaleDTO(tagScale).get()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/tag/progression")
    public ResponseEntity<List<TagProgressionDTO>> getAllTagsProgressions() {
        List<TagProgression> tagProgressions = tagService.getAllTagsProgressions();
        if (tagProgressions.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tagProgressions.stream().map(tagProgression -> tagService.entityToTagProgressionDTO(tagProgression).get()).collect(Collectors.toList()), HttpStatus.OK);
    }

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
        if (response.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response.stream().map(noteService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
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
        return new ResponseEntity(response.stream().map(scaleService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
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
        List<Long> idConcreteScales = concreteScaleService.getAllIds();
        List<ConcreteScale> concreteScales = new ArrayList<>();

        if (idConcreteScales.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        List<ConcreteScaleDTO> response = new ArrayList<>();

        for (Long id : idConcreteScales) {
            List<ConcreteScale> concreteScale = concreteScaleService.getCompleteConcreteScaleById(id);
            response.add(concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScale));
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

        List<ConcreteChordDTO> concreteChordDTOS = new ArrayList<>();
        for (Long id : concreteChordService.getAllIdConcreteChords()) {
            List<ConcreteChord> concreteChordsIt = concreteChordService.getCompleteConcreteChordById(id);
            concreteChordService.concreteChordToConcreteChordDTO(concreteChordsIt).ifPresent(chordDTO -> {
                concreteChordDTOS.add(chordDTO);
            });
        }
        return new ResponseEntity<>(concreteChordDTOS, HttpStatus.OK);
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
    public ResponseEntity<Optional<ProgressionDTO>> findProgression(@RequestBody ProgressionDTO progressionDTO) {
        Progression progression = progressionService.dtotoEntity(progressionDTO);
        Optional<Progression> response = progressionService.get(progression);
        if (!response.isPresent()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Optional.of(progressionService.entityToDTO(response.get())), HttpStatus.OK);
    }

    @PostMapping(path = "/progression/findAll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProgressionDTO>> findAllProgressions(@RequestBody ProgressionDTO progressionDTO) {
        Progression progression = progressionService.dtotoEntity(progressionDTO);
        List<Progression> response = progressionService.getAll(progression);
        if (response.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response.stream().map(progressionService::entityToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(path = "/progression/exist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existProgression(@RequestBody ProgressionDTO progressionDTO) {
        Progression response = progressionService.dtotoEntity(progressionDTO);
        return new ResponseEntity<>(progressionService.exist(response), HttpStatus.OK);
    }


    // Progressiones concretas
    @GetMapping("/progression/concrete")
    public ResponseEntity<List<ConcreteProgressionDTO>> getAllConcreteProgressions() {
        List<Long> concreteProgressionsId = concreteProgressionService.getAllIds();
        if (concreteProgressionsId.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        List<ConcreteProgressionDTO> concreteProgressionDTOS = new ArrayList<>();
        for( Long id: concreteProgressionsId ){
            List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionById(id);
            concreteProgressionService.concreteProgressionToConcreteProgressionDTO(concreteProgressions).ifPresent( concreteProgressionDTO -> concreteProgressionDTOS.add(concreteProgressionDTO) );
        }

        return new ResponseEntity<>(concreteProgressionDTOS, HttpStatus.OK);
    }





    // Intervals de elementos musicales
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

    @PostMapping(path = "/interval/concrete/scale/tonic",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervalsOfScale(@RequestBody ConcreteScaleDTO concreteScaleDto){
        List<Note> notes = concreteScaleDto.getNotes().values().stream().map( noteService::dtotoEntity ).collect(Collectors.toList());
        Note tonic = noteService.dtotoEntity(concreteScaleDto.getTonic());

        List<ConcreteIntervalDTO> concreteIntervals = concreteIntervalService.getConcreteIntervalsWithNotesAndTonic(tonic.getId(), notes.stream().map( note-> note.getId() ).collect(Collectors.toList()));

        return new ResponseEntity<>( concreteIntervals, HttpStatus.OK );
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





    // Chords de elementos musicales
    @PostMapping(path = "/chord/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> getChordsWithIntervals(@RequestBody List<Long> intervalsId){
        List<Interval> intervals = intervalService.getIntervalsById(intervalsId);
        List<Chord> chords = chordService.getChordsWithIntervals(intervals);
        if( chords.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( chords.stream().map( chord -> chord.getId() ).collect(Collectors.toList()), HttpStatus.OK );
    };

    /**
     *
     * @param idIntervals
     * @return id del acorde - id de los intervalos que lo contienen
     */
    @PostMapping(path = "/chord/concrete/interval/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Long, Long[]>> getConcreteChordsWithInterval(@RequestBody List<Long> idIntervals){
        List<ConcreteInterval> interval = idIntervals.stream().map( idInterval -> concreteIntervalService.getConcreteIntervalById(idInterval).get() ).collect(Collectors.toList());

        return new ResponseEntity<>( null, HttpStatus.OK );
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




    // Acordes Concretos de elementos musicales
    @GetMapping("/chord/scale/concrete/cs/{idConcreteScale}")
    public ResponseEntity<Long> getConcreteScaleGradesWithScaleAndTonic(@PathVariable Long idConcreteScale) {
        Long idConcreteScaleGrade = concreteChordService.getIdConcreteScaleWithScale(idConcreteScale);
        if (idConcreteScaleGrade == null) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(idConcreteScaleGrade, HttpStatus.OK);
    }

    @GetMapping("/chord/scale/concrete")
    public ResponseEntity<List<ConcreteScaleGradesDTO>> getAllConcreteScaleGrades() {
        List<Long> concreteScaleGradesIds = concreteChordService.getAllIdConcreteScaleGrades();
        if (concreteScaleGradesIds.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        List<ConcreteScaleGradesDTO> concreteScaleGradesDTOS = new ArrayList<>();
        for(Long id: concreteScaleGradesIds){
            List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradeById(id);
            concreteScaleGradesDTOS.add(concreteChordService.concreteScaleGradesToConcreteScaleGradesDTO(concreteScaleGrades).get());
        }

        return new ResponseEntity<>(concreteScaleGradesDTOS, HttpStatus.OK);
    }

    @GetMapping("/chord/scale")
    public ResponseEntity<List<ScaleGradesDTO>> getAllScaleGrades() {
        List<Long> scaleGradesIds = chordService.getAllIdsScaleGrades();
        if (scaleGradesIds.isEmpty()) return new ResponseEntity(new Message(-1, "Empty"), HttpStatus.NOT_FOUND);

        List<ScaleGradesDTO> scaleGradesDTOS = new ArrayList<>();
        for(Long id: scaleGradesIds)
            scaleGradesDTOS.add(chordService.scaleGradesToScaleGradeDTO(chordService.getScaleGradeById(id)));

        return new ResponseEntity<>(scaleGradesDTOS, HttpStatus.OK);
    }

    @PostMapping(path = "/chord/concrete/scale/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ConcreteScaleGradesDTO>> getConcreteGradesOfScale(@RequestBody ConcreteScaleDTO concreteScaleDTO){
        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( concreteScaleDTO.getId_concrete_scale() );
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        Optional<ConcreteScaleGradesDTO> concreteScaleGradeDTO = concreteChordService.concreteScaleGradesToConcreteScaleGradesDTO(concreteScaleGrades);
        if( !concreteScaleGradeDTO.isPresent() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteScaleGradeDTO, HttpStatus.OK );
    };





    // Scalas de Elementos musicales
    @PostMapping(path = "/scale/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScaleDTO>> getScalesWithIntervals(@RequestBody List<IntervalDTO> intervalDTOS){
        List<Interval> intervals = intervalDTOS.stream().map( intervalService::dtotoEntity ).collect(Collectors.toList());
        List<Scale> scales = scaleService.getScalesWithIntervals(intervals);
        if( scales.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( scales.stream().map( scaleService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };




    // Escalas Concretas de elementos musicales
    @PostMapping(path = "/scale/concrete/interval/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScalesWithConcreteIntervals(@RequestBody ConcreteIntervalDTO concreteIntervalDTO){
        Interval interval = intervalService.getById(concreteIntervalDTO.getId()).get();
        List<Interval> intervals = new ArrayList<>();
        intervals.add(interval);

        List<Scale> scales = scaleService.getScalesWithIntervals(intervals);
        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();
        for(Scale scale: scales){
            List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), concreteIntervalDTO.getFirstNote().getId());
            concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales) );
        }

        return new ResponseEntity<>( concreteScaleDTOS, HttpStatus.OK );
    };

    @PostMapping(path = "/scale/concrete/progression/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConcreteScaleDTO> getConcreteScalesWithConcreteProgression(@RequestBody ConcreteProgressionDTO concreteProgressionDTO){
        List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionById(concreteProgressionDTO.getId_concrete_progression());
        Long idConcreteScale = concreteProgressions.get(0).getConcreteScale().getId().getId_concrete_scale();

        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById(idConcreteScale);

        return new ResponseEntity<>( concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales), HttpStatus.OK );
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

    @PostMapping(path = "/scale/concrete/chord/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScalesWithConcreteChord(@RequestBody ConcreteChordDTO concreteChordDTOS){
        List<Long> idConcreteChords = new ArrayList<>();
        idConcreteChords.add(concreteChordDTOS.getId_concrete_chord());
        List<Long> idConcreteScales = concreteScaleService.getIdConcreteScalesWithConcreteChords(idConcreteChords);

        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();
        for(Long idConcreteScale: idConcreteScales){
            List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId(  idConcreteScale);

            int count = 0;
            for(ConcreteScaleGrade concreteScaleGrade: concreteScaleGrades)
                if( idConcreteChords.contains(concreteScaleGrade.getConcreteChord().getCc_id().getId_concrete_chord()) ) count++;

            if(count >= idConcreteChords.size()){
                List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById( idConcreteScale );
                concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales) );
            }
        }
        return new ResponseEntity<>( concreteScaleDTOS, HttpStatus.OK );
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
        System.out.println(progressionGradeDTO.get().getId_progression_grade());

        return new ResponseEntity<>( progressionGradeDTO, HttpStatus.OK );
    };

    @PostMapping(path = "/progression/concrete/scale/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgressionGradeDTO> getProgressionGradesOfConcreteScale(@RequestBody ConcreteProgressionMEDTO concreteProgressionMEDTO){
        Long idTonic = concreteProgressionMEDTO.getIdTonic();
        Long idProgression = concreteProgressionMEDTO.getIdProgressions().get(0);
        Scale scale = scaleService.getById(concreteProgressionMEDTO.getIdScales().get(0)).get();

        Long idScaleGrade = chordService.getIdScaleGrade(scale);
        Long idConcreteScale = concreteScaleService.getIdConcreteScale(scale.getId(), idTonic);

        Long idProgressionGrade = progressionService.getIdProgressionGradeByScaleGrade(idProgression, idScaleGrade);

        List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionsByProgressionGradeAndConcreteScale(idConcreteScale, idProgressionGrade);


        return new ResponseEntity<>( concreteProgressionService.concreteProgressionToConcreteProgressionDTO(concreteProgressions).get(), HttpStatus.OK );
    };






    // Musical Elements
    @PostMapping(path = "/chord/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteChordDTO>> getConcreteChordsWithME(@RequestBody ConcreteChordMEDTO concreteChordMEDTO){
        List<Long> idChords     = new ArrayList<>(concreteChordMEDTO.getChord());
        List<Long> idIntervals  = new ArrayList<>(concreteChordMEDTO.getIntervals());
        List<Long> idNotes      = new ArrayList<>(concreteChordMEDTO.getNotes());
        Long idTonic = concreteChordMEDTO.getTonic();

        List<ConcreteChordDTO> concreteChordDTOS = new ArrayList<>();
        List<Chord> chords = new ArrayList<>();

        if(!idChords.isEmpty()){
            for(Long idChord: idChords)
                chords.add( chordService.getById(idChord).get() );
        }

        if(!idIntervals.isEmpty()){
            List<Interval> intervals = new ArrayList<>();
            for(Long idInterval: idIntervals)
                intervals.add( intervalService.getById(idInterval).get() );

            chords.addAll(chordService.getChordsWithIntervals(intervals));
        }

        if(chords.isEmpty() && idIntervals.isEmpty()){
            if(idNotes.isEmpty())   return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            concreteChordDTOS = concreteChordService.getConcreteChordsWithNotesAndTonic(idNotes, idTonic);
        } else {
            if(idNotes.isEmpty())   return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);

            for(Chord chord: chords.stream().distinct().collect(Collectors.toList())){
                List<ConcreteChord> bufferConcreteChord = concreteChordService.getCompleteConcreteChordWithTonic(chord.getId(), idTonic);

                int count = 0;
                for(ConcreteChord it: bufferConcreteChord)
                    if( idNotes.contains(it.getNote().getId()) )
                        count++;

                if(count == idNotes.size())
                    concreteChordDTOS.add( concreteChordService.concreteChordToConcreteChordDTO(bufferConcreteChord).get() );
            }
        }

        return new ResponseEntity<>( concreteChordDTOS.stream().distinct().collect(Collectors.toList()), HttpStatus.OK );
    };


    @PostMapping(path = "/scale/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScalesWithME(@RequestBody ConcreteScaleMEDTO concreteScaleMEDTO){
        List<Long> idChords = new ArrayList<>(concreteScaleMEDTO.getChords());
        List<Long> idScales = new ArrayList<>(concreteScaleMEDTO.getScales());
        List<Long> idNotes = new ArrayList<>(concreteScaleMEDTO.getNotes());
        Long idTonic = concreteScaleMEDTO.getTonic();

        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();

        List<Scale> scales = new ArrayList<>();

        if(!idScales.isEmpty()){
            for(Long idScale: idScales)
                scales.add( scaleService.getById(idScale).get() );
        }

        if(!idChords.isEmpty()){
            List<Chord> chord = new ArrayList<>();
            for(Long idChord: idChords)
                chord.add( chordService.getById(idChord).get() );

            scales.addAll(scaleService.getScalesWithChords(chord));
        }

        if(idScales.isEmpty() && idChords.isEmpty()){
            // Solo notas
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            concreteScaleDTOS = concreteScaleService.getConcreteScalesWithTonicAndNotes(idNotes, idTonic);
        } else {
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);

            for(Scale scale: scales.stream().distinct().collect(Collectors.toList())){
                List<ConcreteScale> bufferConcreteScale = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), idTonic);

                int count = 0;
                for(ConcreteScale it: bufferConcreteScale)
                    if( idNotes.contains(it.getNotesOfScale().getId() ))
                        count++;

                if(count == idNotes.size() + 1)
                    concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(bufferConcreteScale));
            }
        }

        return new ResponseEntity<>( concreteScaleDTOS.stream().distinct().collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervalsWithME(@RequestBody ConcreteIntervalMEDTO concreteIntervalMEDTO){
        List<Long> intervalDTOS = concreteIntervalMEDTO.getIntervals();
        List<Long> noteDTOS = concreteIntervalMEDTO.getNotes();
        Long tonicId = concreteIntervalMEDTO.getTonic();

        List<ConcreteIntervalDTO> concreteIntervalDTOS = new ArrayList<>();

        if(intervalDTOS.isEmpty()){
            if(noteDTOS.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            concreteIntervalDTOS = concreteIntervalService.getConcreteIntervalsWithNotesAndTonic(tonicId, noteDTOS);
        } else {
            if(noteDTOS.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);


            for(Long interval: intervalDTOS){
                concreteIntervalDTOS.add( concreteIntervalService.concreteIntervalToConcreteIntervalDTO(concreteIntervalService.getConcreteIntervalWithTonic(interval, tonicId).get()));
            }
            concreteIntervalDTOS.addAll(concreteIntervalService.getConcreteIntervalsWithNotesAndTonic(tonicId, noteDTOS));
        }

        return new ResponseEntity<>( concreteIntervalDTOS.stream().distinct().collect(Collectors.toList()), HttpStatus.OK );
    };



    @PostMapping(path = "/progression/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteProgressionDTO>> getConcreteProgressionWithProgressionScaleAndChord(@RequestBody ConcreteProgressionMEDTO concreteProgressionMEDTO){
        Long idTonic = concreteProgressionMEDTO.getIdTonic();
        List<Long> idNotes = new ArrayList<>(concreteProgressionMEDTO.getIdNotes());
        List<Long> idScales = new ArrayList<>(concreteProgressionMEDTO.getIdScales());
        List<Long> idChords = new ArrayList<>(concreteProgressionMEDTO.getIdChords());
        List<Long> idProgressions   = new ArrayList<>(concreteProgressionMEDTO.getIdProgressions());

        List<ConcreteProgressionDTO> concreteProgressionDTOS = new ArrayList<>();

        List<Scale> scales = new ArrayList<>();
        List<Chord> chords = new ArrayList<>();
        List<Progression> progressions = new ArrayList<>();

        if(!idScales.isEmpty()){
            for(Long idScale: idScales)
                scales.add( scaleService.getById(idScale).get() );
        }

        if(!idChords.isEmpty()){
            for(Long idChord: idChords){
                chords.add( chordService.getById(idChord).get() );
            }

            scales.addAll(scaleService.getScalesWithChords(chords));
        }

        if(!idProgressions.isEmpty()){
            for(Long id: idProgressions)
                progressions.add( progressionService.getById(id).get() );
        }

        if(idScales.isEmpty() && idChords.isEmpty()){
            // Solo notas
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            List<ConcreteScaleDTO> concreteScales = concreteScaleService.getConcreteScalesWithTonicAndNotes(idNotes,idTonic);
            List<Long> idConcreteScales = concreteScales.stream().map(concreteScaleDTO -> concreteScaleDTO.getId_concrete_scale()).collect(Collectors.toList());

            List<ConcreteChordDTO> concreteChords = concreteChordService.getConcreteChordsWithNotesAndTonic(idNotes, idTonic);
            List<Long> idConcreteChords = concreteChords.stream().map(concreteChordDTO -> concreteChordDTO.getId_concrete_chord()).collect(Collectors.toList());

            concreteProgressionDTOS = concreteProgressionService.getCompleteConcreteProgressionsByIdScaleAndChord(idConcreteScales, idConcreteChords, idTonic);
        } else {
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);

            List<Long> idConcreteScales = new ArrayList<>();
            List<Long> idConcreteChords = new ArrayList<>();

            List<Scale> scalesresponsive = new ArrayList<>();
            if(idScales.isEmpty()) scalesresponsive = scales.stream().distinct().collect(Collectors.toList());
            else {
                for(Long idScale: idScales)
                    scalesresponsive.add( scaleService.getById(idScale).get() );
            }

            for(Scale scale: scalesresponsive){
                List<ConcreteScale> bufferConcreteScale = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), idTonic);
                List<ConcreteScaleGrade> buffesScaleGrade = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( bufferConcreteScale.get(0).getId().getId_concrete_scale() );

                int countChords = 0;
                for(ConcreteScaleGrade it: buffesScaleGrade){
                    if( idChords.contains(it.getConcreteChord().getConcreteChord().getId()) ) countChords++;
                }

                int countNotes = 0;
                boolean flag = false;
                for(ConcreteScale it: bufferConcreteScale){
                    if( idNotes.contains(it.getNotesOfScale().getId() )) countNotes++;
                    if( it.getId().getPosition() == 1 && it.getNotesOfScale().getId() == idTonic ){
                        System.out.println(it.getNotesOfScale().getSymbol() + " - " + it.getNotesOfScale().getId() + ":" + idTonic);
                        flag = true;
                    }
                }

                if(countNotes > idNotes.size() && countChords >= idChords.size() && flag){
                    idConcreteChords.addAll(buffesScaleGrade.stream().map(concreteScaleGrade -> concreteScaleGrade.getConcreteChord().getCc_id().getId_concrete_chord()).collect(Collectors.toList()));
                    idConcreteScales.add( bufferConcreteScale.get(0).getId().getId_concrete_scale() );
                }
            }

            concreteProgressionDTOS.addAll(concreteProgressionService.getCompleteConcreteProgressionsByIdScaleAndChord( idConcreteScales.stream().distinct().collect(Collectors.toList()), idConcreteChords.stream().distinct().collect(Collectors.toList()), idTonic));
        }

        List<ConcreteProgressionDTO> response = new ArrayList<>();

        if(!idProgressions.isEmpty()){
            for(ConcreteProgressionDTO concreteProgressionDTO: concreteProgressionDTOS)
                if( idProgressions.contains( concreteProgressionDTO.getId() ) ) response.add( concreteProgressionDTO );
        } else response = concreteProgressionDTOS;


        return new ResponseEntity<>( response, HttpStatus.OK );
    };

    @GetMapping("/chord/complete/{concreteChordId}")
    public ResponseEntity<ChordAnalizedDTO> analizeChord(@PathVariable Long concreteChordId){

        HashMap<Long, List<Integer>> concreteProgressionsIds = concreteChordService.getConcreteProgressionsIdWithConcreteChordId(concreteChordId);

        List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordById(concreteChordId);
        Long idTonic = 0L;
        for( ConcreteChord concreteChord: concreteChords )
            if( concreteChord.getCc_id().getPosition_note_chord() == 1 )
                idTonic = concreteChord.getNote().getId();

        List<Interval> intervals = intervalService.getIntervalsOfChord(concreteChords.get(0).getConcreteChord());
        List<Long> concreteIntervalsIds = new ArrayList<>();
        for(Interval interval: intervals)
            concreteIntervalsIds.add( concreteIntervalService.getConcreteIntervalWithTonic(interval.getId(), idTonic).get().getId_concrete_interval() );


        HashMap<Long, Integer> concreteScalesIds = new HashMap<>();
        for(Long id: concreteChordService.getConcreteScaleGradeIdWithConcreteChordId(concreteChordId)){
            List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradeById(id);
            for(int i = 0; i < concreteScaleGrades.size(); i++){
                if( concreteScaleGrades.get(i).getConcreteChord().getCc_id().getId_concrete_chord() == concreteChordId &&
                        concreteScaleGrades.get(i).getId().getPosition_note_chord() == 1){
                    concreteScalesIds.put( concreteScaleGrades.get(i).getId().getId_concrete_scale(), Math.round(i/3));
                }
             }
        }

        ChordAnalizedDTO chordAnalizedDTO = new ChordAnalizedDTO();
        chordAnalizedDTO.setConcreteProgressionsIds(concreteProgressionsIds);
        chordAnalizedDTO.setConcreteIntervalsIds(concreteIntervalsIds);
        chordAnalizedDTO.setConcreteScalesIds(concreteScalesIds);

        return new ResponseEntity<>( chordAnalizedDTO, HttpStatus.OK );
    }

    @GetMapping("/scale/complete/{concreteScaleId}")
    public ResponseEntity<ScaleAnalizedDTO> analizeScale(@PathVariable Long concreteScaleId){

        List<Long> concreteProgressionsIds = concreteProgressionService.getConcreteProgressionsIdsByConcreteScaleId(concreteScaleId);
        List<Long> concreteChordsIds = concreteChordService.getConcreteChordsIdByConcreteScaleId(concreteScaleId);
        List<Long> concreteIntervalsIds = concreteIntervalService.getIdConcreteIntervalsOfConcreteScale(concreteScaleId);

        ScaleAnalizedDTO scaleAnalizedDTO = new ScaleAnalizedDTO();
        scaleAnalizedDTO.setConcreteIntervalsIds(concreteIntervalsIds);
        scaleAnalizedDTO.setConcreteProgressionsIds(concreteProgressionsIds);
        scaleAnalizedDTO.setConcreteChordsIds(concreteChordsIds);

        return new ResponseEntity<>( scaleAnalizedDTO, HttpStatus.OK );
    }
}