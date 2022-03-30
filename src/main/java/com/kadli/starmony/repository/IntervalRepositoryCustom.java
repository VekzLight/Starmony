package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.List;

public interface IntervalRepositoryCustom extends CustomCrudRepository<Interval, Long>{
    List<Interval> getIntervalsOfChord(Chord chord);
    List<Interval> getIntervalsOfChords(List<Chord> chords);

    Interval getIntervalOfNotes(List<Note> notes);
    List<Interval> getIntervalsOfNotes(List<Note> notes);

    Interval getIntervalWithSemitone(Integer semitone);
    List<Interval> getIntervalsWithSemitones(List<Integer> semitones);

    List<Interval> getIntervalsOfScale(Scale scale);
    List<Interval> getIntervalsOfScales(List<Scale> scales);

    List<Interval> getAllIntervalsOfScale(Scale scale);
    List<Interval> getAllIntervalsOfScales(List<Scale> scales);

    List<Interval> getIntervalsOfChordId(Long id);
    List<Interval> getIntervalsOfChordsId(List<Long> ids);

    Interval getIntervalOfNotesId(List<Long> ids);
    List<Interval> getIntervalsOfNotesId(List<Long> ids);

    List<Interval> getIntervalsOfScaleId(Long id);
    List<Interval> getIntervalsOfScalesId(List<Long> ids);

    List<Interval> getAllIntervalsOfScaleId(Long id);
    List<Interval> getAllIntervalsOfScalesId(List<Long> ids);
}
