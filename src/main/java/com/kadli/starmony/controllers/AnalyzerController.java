package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ChordRepository;
import com.kadli.starmony.repository.IntervalRepository;
import com.kadli.starmony.repository.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyzerController {

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private ChordRepository chordRepository;

    @Autowired
    private ScaleRepository scaleRepository;



    // Principales
    @PostMapping(path = "/api/getIntervals/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOf(@RequestBody Map<String, List<Long>> musicalElements){
        List<Interval> out = new ArrayList<>();
        List<Long> progressionsId = new ArrayList<>();
        List<Long> intervalsId = new ArrayList<>();
        List<Long> chordsId = new ArrayList<>();
        List<Long> scalesId = new ArrayList<>();
        List<Long> notesId = new ArrayList<>();

        if(musicalElements.containsKey("progression")) progressionsId.addAll(musicalElements.get("progression"));
        if(musicalElements.containsKey("interval")) intervalsId.addAll(musicalElements.get("interval"));
        if(musicalElements.containsKey("chord")) chordsId.addAll(musicalElements.get("chord"));
        if(musicalElements.containsKey("scale")) scalesId.addAll(musicalElements.get("scale"));
        if(musicalElements.containsKey("note")) notesId.addAll(musicalElements.get("note"));

        if(chordsId.size() == 1) out.addAll(intervalRepository.getIntervalsOfChordId(chordsId.get(0)));
        if(scalesId.size() == 1) out.addAll(intervalRepository.getIntervalsOfScaleId(scalesId.get(0)));

        if(chordsId.size() > 1) out.addAll(intervalRepository.getIntervalsOfChordsId(chordsId));
        if(scalesId.size() > 1) out.addAll(intervalRepository.getIntervalsOfScalesId(scalesId));

        if(notesId.size() == 2) out.add(intervalRepository.getIntervalOfNotesId(notesId));
        if(notesId.size() > 2) out.addAll(intervalRepository.getIntervalsOfNotesId(notesId));

        return out.stream().distinct().collect(Collectors.toList());
    };

    @PostMapping(path = "/api/getChords/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOf(@RequestBody Map<String, List<Long>> musicalElements){
        List<Chord> out = new ArrayList<>();
        List<Long> progressionsId = new ArrayList<>();
        List<Long> intervalsId = new ArrayList<>();
        List<Long> chordsId = new ArrayList<>();
        List<Long> scalesId = new ArrayList<>();
        List<Long> notesId = new ArrayList<>();

        if(musicalElements.containsKey("progression")) progressionsId.addAll(musicalElements.get("progression"));
        if(musicalElements.containsKey("interval")) intervalsId.addAll(musicalElements.get("interval"));
        if(musicalElements.containsKey("chord")) chordsId.addAll(musicalElements.get("chord"));
        if(musicalElements.containsKey("scale")) scalesId.addAll(musicalElements.get("scale"));
        if(musicalElements.containsKey("note")) notesId.addAll(musicalElements.get("note"));


        if(intervalsId.size() == 1) out.addAll(chordRepository.getChordsWithIntervalId(intervalsId.get(0)));
        if(scalesId.size() == 1) out.addAll(chordRepository.getChordsOfScaleId(scalesId.get(0)));

        if(intervalsId.size() > 1) out.addAll(chordRepository.getChordsWithIntervalsId(intervalsId));
        if(scalesId.size() > 1) out.addAll(chordRepository.getChordsOfScalesId(scalesId));

        return out.stream().distinct().collect(Collectors.toList());
    };

    @PostMapping(path = "/api/getScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesOf(@RequestBody Map<String, List<Long>> musicalElements){
        List<Scale> out = new ArrayList<>();
        List<Long> progressionsId = new ArrayList<>();
        List<Long> intervalsId = new ArrayList<>();
        List<Long> chordsId = new ArrayList<>();
        List<Long> scalesId = new ArrayList<>();
        List<Long> notesId = new ArrayList<>();

        if(musicalElements.containsKey("progression")) progressionsId.addAll(musicalElements.get("progression"));
        if(musicalElements.containsKey("interval")) intervalsId.addAll(musicalElements.get("interval"));
        if(musicalElements.containsKey("chord")) chordsId.addAll(musicalElements.get("chord"));
        if(musicalElements.containsKey("scale")) scalesId.addAll(musicalElements.get("scale"));
        if(musicalElements.containsKey("note")) notesId.addAll(musicalElements.get("note"));


        if(intervalsId.size() == 1) out.addAll(scaleRepository.getScalesWithIntervalId(intervalsId.get(0)));
        if(chordsId.size() == 1) out.addAll(scaleRepository.getScalesWithChordId(chordsId.get(0)));

        if(intervalsId.size() > 1) out.addAll(scaleRepository.getScalesWithIntervalsId(intervalsId));
        if(chordsId.size() > 1) out.addAll(scaleRepository.getScalesWithChordsId(chordsId));

        return out.stream().distinct().collect(Collectors.toList());
    };



    // Intervals
    // POST - Single Element
    @PostMapping(path = "/api/getIntervalsOfChord/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfChord(@RequestBody Chord chord){
        return intervalRepository.getIntervalsOfChord(chord);
    };

    @PostMapping(path = "/api/getIntervalsOfScale/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfScale(@RequestBody Scale scale){
        return intervalRepository.getIntervalsOfScale(scale);
    };

    @PostMapping(path = "/api/getAllIntervalsOfScale/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getAllIntervalsOfScale(@RequestBody Scale scale){
        return intervalRepository.getAllIntervalsOfScale(scale);
    };



    // POST - Multiple Elements
    @PostMapping(path = "/api/getIntervalsOfChords/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfChords(@RequestBody List<Chord> chords){
        return intervalRepository.getIntervalsOfChords(chords);
    };

    @PostMapping(path = "/api/getIntervalOfNotes/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Interval getIntervalOfNotes(@RequestBody List<Note> notes){
        return intervalRepository.getIntervalOfNotes(notes);
    };

    @PostMapping(path = "/api/getIntervalsOfNotes/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfNotes(@RequestBody List<Note> notes){
        return intervalRepository.getIntervalsOfNotes(notes);
    };

    @PostMapping(path = "/api/getIntervalsWithSemitones/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsWithSemitones(@RequestBody List<Integer> semitones){
        return intervalRepository.getIntervalsWithSemitones(semitones);
    };

    @PostMapping(path = "/api/getIntervalsOfScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfScales(@RequestBody List<Scale> scales){
        return intervalRepository.getIntervalsOfScales(scales);
    };

    @PostMapping(path = "/api/getAllIntervalsOfScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getAllIntervalsOfScales(@RequestBody List<Scale> scales){
        return intervalRepository.getAllIntervalsOfScales(scales);
    };

    @PostMapping(path = "/api/getIntervalsOfChordsId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfChordsId(@RequestBody List<Long> ids){
        return intervalRepository.getIntervalsOfChordsId(ids);
    };

    @PostMapping(path = "/api/getIntervalOfNotesId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Interval getIntervalOfNotesId(@RequestBody List<Long> ids){
        return intervalRepository.getIntervalOfNotesId(ids);
    };

    @PostMapping(path = "/api/getIntervalsOfNotesId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfNotesId(@RequestBody List<Long> ids){
        return intervalRepository.getIntervalsOfNotesId(ids);
    };

    @PostMapping(path = "/api/getIntervalsOfScalesId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfScalesId(@RequestBody List<Long> ids){
        return intervalRepository.getIntervalsOfScalesId(ids);
    };

    @PostMapping(path = "/api/getAllIntervalsOfScalesId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getAllIntervalsOfScalesId(@RequestBody List<Long> ids){
        return intervalRepository.getAllIntervalsOfScalesId(ids);
    };



    // GET
    @GetMapping("/api/getIntervalWithSemitone/{semitone}")
    public Interval getIntervalWithSemitone(@PathVariable int semitone){
        return intervalRepository.getIntervalWithSemitone(semitone);
    };

    @GetMapping("/api/getIntervalsOfChordId/{id}")
    public List<Interval> getIntervalsOfChordId(@PathVariable Long id){
        return intervalRepository.getIntervalsOfChordId(id);
    };

    @GetMapping("/api/getIntervalsOfScaleId/{id}")
    public List<Interval> getIntervalsOfScaleId(@PathVariable Long id){
        return intervalRepository.getIntervalsOfScaleId(id);
    };

    @GetMapping("/api/getAllIntervalsOfScaleId/{id}")
    public List<Interval> getAllIntervalsOfScaleId(@PathVariable Long id){
        return intervalRepository.getAllIntervalsOfScaleId(id);
    };



    // Chords
    // POST - Single Element
    @PostMapping(path = "/api/getChordsWithInterval/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsWithInterval(@RequestBody Interval interval){
        return chordRepository.getChordsWithInterval(interval);
    };

    @PostMapping(path = "/api/getChordsWithIntervals/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsWithIntervals(@RequestBody List<Interval> intervals){
        return chordRepository.getChordsWithIntervals(intervals);
    };

    @PostMapping(path = "/api/getChordsOfScale/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScale(@RequestBody Scale scale){
        return chordRepository.getChordsOfScale(scale);
    };



    // POST - Multiple Elements
    @PostMapping(path = "/api/getChordsOfScalesId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScalesId(@RequestBody List<Long> ids){
        return chordRepository.getChordsOfScalesId(ids);
    };

    @PostMapping(path = "/api/getChordsWithIntervalsId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsWithIntervalsId(List<Long> ids){
        return chordRepository.getChordsWithIntervalsId(ids);
    };

    @PostMapping(path = "/api/getChordsOfScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScales(@RequestBody List<Scale> scales){
        return chordRepository.getChordsOfScales(scales);
    };



    // GET
    @GetMapping("/api/getChordsWithIntervalId/{id}")
    public List<Chord> getChordsWithIntervalId(Long id){
        return chordRepository.getChordsWithIntervalId(id);
    };

    @GetMapping("/api/getChordsOfScaleId/{id}")
    public List<Chord> getChordsOfScaleId(@RequestBody Long id){
        return chordRepository.getChordsOfScaleId(id);
    };



    // Scales
    // POST - Single Element
    @PostMapping(path = "/api/getScalesWithInterval/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesWithInterval(Interval interval){
        return scaleRepository.getScalesWithInterval(interval);
    };

    @PostMapping(path = "/api/getScalesWithChord/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesWithChord(Chord chord){
        return scaleRepository.getScalesWithChord(chord);
    };

    @PostMapping(path = "/api/getScaleWithCode/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Scale getScaleWithCode(String code){
        return scaleRepository.getScaleWithCode(code);
    };

    // POST - Multiple Elements
    @PostMapping(path = "/api/getScalesWithIntervals/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesWithIntervals(List<Interval> intervals){
        return scaleRepository.getScalesWithIntervals(intervals);
    };

    @PostMapping(path = "/api/getScalesWithChords/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesWithChords(List<Chord> chords){
        return scaleRepository.getScalesWithChords(chords);
    };

    @PostMapping(path = "/api/getScalesWithIntervalsId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesWithIntervalsId(List<Long> ids){
        return scaleRepository.getScalesWithIntervalsId(ids);
    };

    @PostMapping(path = "/api/getScalesWithChordsId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scale> getScalesWithChordsId(List<Long> ids){
        return scaleRepository.getScalesWithChordsId(ids);
    };



    // GET
    @GetMapping("/api/getScalesWithIntervalId/{id}")
    public List<Scale> getScalesWithIntervalId(@PathVariable Long id){
        return scaleRepository.getScalesWithIntervalId(id);
    }

    @GetMapping("/api/getScalesWithChordId/{id}")
    public List<Scale> getScalesWithChordId(Long id){
        return scaleRepository.getScalesWithChordId(id);
    };



}