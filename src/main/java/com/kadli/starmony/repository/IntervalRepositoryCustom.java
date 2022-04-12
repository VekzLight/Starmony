package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Interval;

import java.util.List;
import java.util.Optional;

public interface IntervalRepositoryCustom extends CustomCrudRepository<Interval, Long>{

    // Obtener Intervalos de Semitonos
    Optional<Interval> getIntervalWithSemitones(Integer semitones);
    List<Interval> getIntervalsWithSemitones(List<Integer> semitones);

    // Obtener Intervalos de Acordes
    List<Interval> getIntervalsOfChord(Long id);
    List<Interval> getIntervalsOfChords(List<Long> ids);

    // Obtener Intervalos de Notas
    Optional<Interval> getIntervalOfNotes(Long note1, Long note2);
    List<Interval> getIntervalsOfNotes(List<Long> ids);

    // Obtener Intervalos de Escalas
    List<Interval> getIntervalsOfScaleCodeByTonic(String scaleCode);
    List<Interval> getIntervalsOfScaleCodeByAll(String scaleCode);

}
