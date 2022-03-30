package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;

import java.util.List;

public interface ScaleRepositoryCustom extends CustomCrudRepository<Scale, Long>{
    List<Scale> getScalesWithInterval(Interval interval);
    List<Scale> getScalesWithIntervals(List<Interval> intervals);

    List<Scale> getScalesWithChord(Chord chord);
    List<Scale> getScalesWithChords(List<Chord> chords);

    List<Scale> getScalesWithIntervalId(Long id);
    List<Scale> getScalesWithIntervalsId(List<Long> ids);

    List<Scale> getScalesWithChordId(Long id);
    List<Scale> getScalesWithChordsId(List<Long> ids);

    Scale getScaleWithCode(String code);
}
