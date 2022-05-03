package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteIntervalDTO;
import com.kadli.starmony.entity.ConcreteInterval;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;

import java.util.List;
import java.util.Optional;

public interface ConcreteIntervalService {

    // Obtencion
    List<ConcreteInterval> getAllConcreteIntervals();
    Optional<ConcreteInterval> getConcreteIntervalById(Long id);
    Optional<ConcreteInterval> getConcreteIntervalWithTonic(Long idInterval, Long idTonic);

    // Generadores
    ConcreteInterval generateConcreteInterval(Interval interval, Note tonic);
    ConcreteInterval generateConcreteIntervalAndSave(Interval interval, Note tonic);
    List<ConcreteInterval> generateAllConcreteIntervalsAndSave();

    // Conversiones
    ConcreteIntervalDTO concreteIntervalToConcreteIntervalDTO(ConcreteInterval concreteInterval);
    List<ConcreteIntervalDTO> concreteIntervalsToConcreteIntervalDTOS(List<ConcreteInterval> concreteIntervals);



}
