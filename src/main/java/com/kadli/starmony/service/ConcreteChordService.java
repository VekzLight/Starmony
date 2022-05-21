package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.ConcreteProgressionDTO;
import com.kadli.starmony.dto.ConcreteScaleGradesDTO;
import com.kadli.starmony.entity.*;

import java.util.List;
import java.util.Optional;

public interface ConcreteChordService {

    // Obtener
    List<ConcreteChord> getAllConcreteChords();
    List<ConcreteChord> getCompleteConcreteChordById(Long id);
    List<ConcreteChord> getCompleteConcreteChordWithTonic(Long idChord, Long idTonic);

    List<ConcreteScaleGrade> getCompleteConcreteScaleGradesByConcreteScaleId(Long idConcrete);



    // Generar
    // TODO GENERAR LA ID DEL
    List<ConcreteScaleGrade> generateConcreteGradesOfScale(List<ConcreteScale> concreteScales);
    List<ConcreteScaleGrade> generateAndSaveConcreteGradesOfScale(List<ConcreteScale> concreteScales);
    List<ConcreteScaleGrade> generateAndSaveAllConcreteGradesOfScale();

    List<ConcreteChord> generateConcreteChords(Chord chord, Note tonic);
    List<ConcreteChord> generateAndSaveConcreteChords(Chord chord, Note tonic);
    List<ConcreteChord> generateAndSaveAllConcretechords();

    List<ConcreteChordDTO> getConcreteChordsWithNotesAndTonic( List<Long> idNotes, Long tonic );





    // Utilidades
    Long getLastConcreteChordId();
    Long getIdConcreteChord(Long idChord, Long idTonic);

    // Convesiones DTO
    Optional<ConcreteChordDTO> concreteChordToConcreteChordDTO(List<ConcreteChord> concreteChords);
    Optional<ConcreteScaleGradesDTO> concreteScaleGradesToConcreteScaleGradesDTO(List<ConcreteScaleGrade> concreteScaleGrades);

    Long getLastConcreteGradeScaleId();


}
