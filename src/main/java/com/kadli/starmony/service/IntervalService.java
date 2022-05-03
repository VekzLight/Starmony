package com.kadli.starmony.service;

import com.kadli.starmony.dto.IntervalDTO;
import com.kadli.starmony.entity.*;

import java.util.List;
import java.util.Optional;

public interface IntervalService extends CustomCrudService<Interval, Long>, DtoConversions<Interval, IntervalDTO>{

    //
    // Obtener Intervalos de Escalas
    //

    List<Interval> getIntervalsOfScaleByTonic(Scale scale);
    List<Interval> getIntervalsOfScaleByAll(Scale scale);


    //
    // Intervalos de Acordes
    //

    // Obtener
    List<Interval> getIntervalsOfChord(Chord chord);



    // Generadores
    List<Interval> generateIntervalsOfChord(Chord chord);
    List<ChordInterval> generateIntervalsOfChordAndSave(Chord chord);
    List<ChordInterval> generateAllIntervalsOfChordsAndSave();


    // Obtener Intervalos por semitonos
    Optional<Interval> getIntervalWithSemitone(int semitone);

    List<Interval> getIntervalsOfNotes(List<Note> notes);

    Long getLastId();
}
