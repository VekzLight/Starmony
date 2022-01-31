package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;

import java.util.List;

public interface ScaleRepository {

    public List<Scale> getScalesWithInterval(Interval interval);
    public List<Scale> getScalesWithIntervals(List<Interval> intervals);

    public List<Scale> getScalesWithChord(Chord chord);
    public List<Scale> getScalesWithChords(List<Chord> chords);

    public List<Scale> getScalesWithIntervalId(Long id);
    public List<Scale> getScalesWithIntervalsId(List<Long> ids);

    public List<Scale> getScalesWithChordId(Long id);
    public List<Scale> getScalesWithChordsId(List<Long> ids);

    public Scale getScaleWithCode(String code);

}
