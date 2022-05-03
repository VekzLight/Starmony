package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ChordRepositoryCustom extends CustomCrudRepository<Chord, Long>{
    List<Chord> getChordsWithIntervals(List<Interval> intervals);
    List<Chord> getChordsWithIntervalsId(List<Long> ids, String expressionBase, List<String> expressions);
    List<Chord> getChordsOfScale(Scale scale);

    List<Chord> getChordsOfScaleId(Long id);
    List<Chord> getChordsOfScalesId(List<Long> ids);

    Optional<Chord>  getChordWithIntervals(List<Interval> intervals);
    Optional<Chord> getChordWithIntervalsId(List<Long> ids, String expressionBase, List<String> expressions);

}
