package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.ConcreteChord;
import com.kadli.starmony.entity.Note;

import java.util.List;

public interface ConcreteChordService {

    // Obtener
    List<ConcreteChord> getAllConcreteChords();
    List<ConcreteChord> getConcreteChordWithTonic(Chord chord, Note tonic);


    // Generar
    List<ConcreteChord> generateConcreteChords(Chord chord, Note tonic);
    List<ConcreteChord> generateAndSaveConcreteChords(Chord chord, Note tonic);
    List<ConcreteChord> generateAndSaveAllConcretechords();


    // Utilidades
    Long getLastConcreteChordId();


    // Convesiones DTO
    ConcreteChordDTO concreteChordToConcreteChordDTO(List<ConcreteChord> concreteChords);
}
