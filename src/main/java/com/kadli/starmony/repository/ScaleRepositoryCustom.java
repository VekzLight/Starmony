package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;

import java.util.List;
import java.util.Optional;

public interface ScaleRepositoryCustom extends CustomCrudRepository<Scale, Long>{
    List<Scale> getScalesWithIntervals(List<Interval> intervals);
    List<Scale> getScalesWithChords(List<Chord> chords);

    List<Scale> getScalesWithIntervalId(Long id);
    List<Scale> getScalesWithIntervalsId(List<Long> ids);

    List<Scale> getScalesWithChordId(Long id);
    List<Scale> getScalesWithChordsId(List<Long> ids);

    Optional<Scale> getScaleWithCode(String code);
}
