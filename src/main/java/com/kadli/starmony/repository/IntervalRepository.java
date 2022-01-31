package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.List;

public interface IntervalRepository {

    public List<Interval> getIntervalsOfChord(Chord chord);
    public List<Interval> getIntervalsOfChords(List<Chord> chords);

    public Interval getIntervalOfNotes(List<Note> notes);
    public List<Interval> getIntervalsOfNotes(List<Note> notes);

    public Interval getIntervalWithSemitone(Integer semitone);
    public List<Interval> getIntervalsWithSemitones(List<Integer> semitones);

    public List<Interval> getIntervalsOfScale(Scale scale);
    public List<Interval> getIntervalsOfScales(List<Scale> scales);

    public List<Interval> getAllIntervalsOfScale(Scale scale);
    public List<Interval> getAllIntervalsOfScales(List<Scale> scales);

    public List<Interval> getIntervalsOfChordId(Long id);
    public List<Interval> getIntervalsOfChordsId(List<Long> ids);

    public Interval getIntervalOfNotesId(List<Long> ids);
    public List<Interval> getIntervalsOfNotesId(List<Long> ids);

    public List<Interval> getIntervalsOfScaleId(Long id);
    public List<Interval> getIntervalsOfScalesId(List<Long> ids);

    public List<Interval> getAllIntervalsOfScaleId(Long id);
    public List<Interval> getAllIntervalsOfScalesId(List<Long> ids);

}
