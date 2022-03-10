package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.List;

public interface ChordRepositoryCustom {
    public List<Chord> getChordsWithInterval(Interval interval);
    public List<Chord> getChordsWithIntervals(List<Interval> intervals);

    public List<Chord> getChordsOfScale(Scale scale);
    public List<Chord> getChordsOfScales(List<Scale> scales);

    public List<Chord> getChordsWithIntervalId(Long id);
    public List<Chord> getChordsWithIntervalsId(List<Long> ids);

    public List<Chord> getChordsOfScaleId(Long id);
    public List<Chord> getChordsOfScalesId(List<Long> ids);

    public List<Chord> getChordWithNotes(List<Note> notes);
}
