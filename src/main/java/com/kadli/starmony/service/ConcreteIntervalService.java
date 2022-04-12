package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteIntervalDTO;
import com.kadli.starmony.entity.ConcreteInterval;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;

import java.util.List;

public interface ConcreteIntervalService {

    // Obtencion
    ConcreteInterval getConcreteInterval(Interval interval, Note tonic, Note note);
    List<ConcreteInterval> getConcreteIntervals(List<Interval> intervals, Note tonic, Note note);

    // Generadores
    ConcreteInterval generateConcreteInterval(Interval interval, Note tonic);
    ConcreteInterval generateConcreteIntervalAndSave(Interval interval, Note tonic);
    List<ConcreteInterval> generateAllConcreteIntervalsAndSave();

    // Conversiones
    ConcreteIntervalDTO concreteIntervalToConcreteIntervalDTO(ConcreteInterval concreteInterval);
    List<ConcreteIntervalDTO> concreteIntervalsToConcreteIntervalDTOS(List<ConcreteInterval> concreteIntervals);
}
