package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.*;
import com.kadli.starmony.interfaces.MusicalElement;
import com.kadli.starmony.repository.ChordRepository;
import com.kadli.starmony.repository.IntervalRepository;
import com.kadli.starmony.repository.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AnalyzerController {

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private ChordRepository chordRepository;

    @Autowired
    private ScaleRepository scaleRepository;

    @PostMapping(path = "/getIntervals/",
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

        return out;
    };

    @PostMapping(path = "/getChords/",
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

        return out;
    };

    @PostMapping(path = "/getScales/",
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

        return out;
    };

    @PostMapping(path = "/getIntervalsOfChord/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfChord(@RequestBody Chord chord){
        return intervalRepository.getIntervalsOfChord(chord);
    };

    @PostMapping(path = "/getIntervalsOfChords/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfChords(@RequestBody List<Chord> chords){
        return intervalRepository.getIntervalsOfChords(chords);
    };

    @PostMapping(path = "/getIntervalOfNotes/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Interval getIntervalsOfChord(@RequestBody List<Note> notes){
        return intervalRepository.getIntervalOfNotes(notes);
    };

    @PostMapping(path = "/getIntervalsOfNotes/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfNotes(@RequestBody List<Note> notes){
        return intervalRepository.getIntervalsOfNotes(notes);
    };

    @GetMapping("/getIntervalWithSemitone/{semitone}")
    public Interval getIntervalWithSemitone(@PathVariable int semitone){
        return intervalRepository.getIntervalWithSemitone(semitone);
    };

    @PostMapping(path = "/getIntervalsWithSemitones/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsWithSemitones(@RequestBody List<Integer> semitones){
        return intervalRepository.getIntervalsWithSemitones(semitones);
    };

    @PostMapping(path = "/getIntervalsOfScale/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfScale(@RequestBody Scale scale){
        return intervalRepository.getIntervalsOfScale(scale);
    };

    @PostMapping(path = "/getIntervalsOfScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getIntervalsOfScales(@RequestBody List<Scale> scales){
        return intervalRepository.getIntervalsOfScales(scales);
    };


    @PostMapping(path = "/getIAllntervalsOfScale/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getAllIntervalsOfScale(@RequestBody Scale scale){
        return intervalRepository.getAllIntervalsOfScale(scale);
    };

    @PostMapping(path = "/getAllIntervalsOfScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Interval> getAllIntervalsOfScales(@RequestBody List<Scale> scales){
        return intervalRepository.getAllIntervalsOfScales(scales);
    };

    @PostMapping(path = "/getChordsWithInterval/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsWithInterval(@RequestBody Interval interval){
        return chordRepository.getChordsWithInterval(interval);
    };

    @PostMapping(path = "/getChordsWithIntervals/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsWithIntervals(@RequestBody List<Interval> intervals){
        return chordRepository.getChordsWithIntervals(intervals);
    };

    @PostMapping(path = "/getChordsOfScaleId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScaleId(@RequestBody Long scale){
        return chordRepository.getChordsOfScaleId(scale);
    };

    @PostMapping(path = "/getChordsOfScalesId/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScalesId(@RequestBody List<Long> ids){
        return chordRepository.getChordsOfScalesId(ids);
    };

    @PostMapping(path = "/getChordsOfScale/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScale(@RequestBody Scale scale){
        return chordRepository.getChordsOfScale(scale);
    };

    @PostMapping(path = "/getChordsOfScales/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chord> getChordsOfScales(@RequestBody List<Scale> scales){
        return chordRepository.getChordsOfScales(scales);
    };

    @GetMapping("/getScalesWithIntervalId/{id}")
    public List<Scale> getScalesWithIntervalId(@PathVariable Long id){
        return scaleRepository.getScalesWithIntervalId(id);
    }
}