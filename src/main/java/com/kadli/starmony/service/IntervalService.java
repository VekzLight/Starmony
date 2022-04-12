package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ConcreteIntervalDTO;
import com.kadli.starmony.dto.IntervalDTO;
import com.kadli.starmony.entity.*;
import org.springframework.stereotype.Service;

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
    void generateIntervalsOfChordAndSave(Chord chord);
    void generateAllIntervalsOfChordsAndSave();


    // Obtener Intervalos por semitonos
    Optional<Interval> getIntervalWithSemitone(int semitone);
}
