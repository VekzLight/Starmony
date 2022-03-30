package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.List;
import java.util.Optional;

public interface ChordRepositoryCustom extends CustomCrudRepository<Chord, Long>{
    List<Chord> getChordsWithInterval(Interval interval);
    List<Chord> getChordsWithIntervals(List<Interval> intervals);

    List<Chord> getChordsOfScale(Scale scale);
    List<Chord> getChordsOfScales(List<Scale> scales);

    List<Chord> getChordsWithIntervalId(Long id);
    List<Chord> getChordsWithIntervalsId(List<Long> ids);

    List<Chord> getChordsOfScaleId(Long id);
    List<Chord> getChordsOfScalesId(List<Long> ids);

    List<Chord> getChordWithNotes(List<Note> notes);

    List<Chord>  getChordWithIntervals(List<Interval> intervals);
    List<Chord>  getChordWithIntervalsId(List<Long> ids);

    List<Chord> getConcreteChords();
}
