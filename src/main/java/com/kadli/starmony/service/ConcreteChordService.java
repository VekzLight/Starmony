package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.ConcreteScaleGradesDTO;
import com.kadli.starmony.entity.*;

import java.util.HashMap;
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
    HashMap<Long, List<ConcreteScaleGrade>> generateConcreteGradesOfScales(List<ConcreteScale> concreteScales, HashMap<Long, List<ScaleGrade>> scaleGrades );
    List<ConcreteScaleGrade> generateConcreteGradesOfScale(List<ConcreteScale> concreteScales, HashMap<Long, List<ScaleGrade>> scaleGrades);
    List<ConcreteScaleGrade> generateAndSaveConcreteGradesOfScale(List<ConcreteScale> concreteScales, HashMap<Long, List<ScaleGrade>> scaleGrades);
    List<ConcreteScaleGrade> generateAndSaveAllConcreteGradesOfScale();

    List<ConcreteChord> generateConcreteChords(Chord chord, Note tonic);
    List<ConcreteChord> generateAndSaveConcreteChords(Chord chord, Note tonic);
    List<ConcreteChord> generateAndSaveAllConcretechords();

    List<ConcreteChordDTO> getConcreteChordsWithNotesAndTonic( List<Long> idNotes, Long tonic );





    // Utilidades
    Long getLastConcreteChordId();
    Long getIdConcreteChord(Long idChord, Long idTonic);
    List<Long> getAllIdConcreteChords();

    // Convesiones DTO
    Optional<ConcreteChordDTO> concreteChordToConcreteChordDTO(List<ConcreteChord> concreteChords);
    Optional<ConcreteScaleGradesDTO> concreteScaleGradesToConcreteScaleGradesDTO(List<ConcreteScaleGrade> concreteScaleGrades);

    Long getLastConcreteGradeScaleId();


    List<Long> getConcreteScaleGradeIdWithConcreteChordId(Long concreteChordId);

    HashMap<Long, List<Integer>> getConcreteProgressionsIdWithConcreteChordId(Long concreteChordId);

    List<ConcreteScaleGrade> getCompleteConcreteScaleGradeById(Long id);

    Chord getIdChordOfIdConcreteChord(Long concreteChordId);

    List<Long> getAllIdConcreteScaleGrades();

    Long getIdConcreteScaleWithScale(Long idConcreteScale);

    List<Long> getConcreteChordsIdByConcreteScaleId(Long concreteScaleId);

}
