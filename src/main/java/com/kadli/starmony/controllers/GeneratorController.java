package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/generator")
@CrossOrigin(origins = "http://localhost:4200")
public class GeneratorController {


    // Elementos musicales Abstractos
    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private NoteService noteService;


    // Elementos musicales Concretos
    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ProgressionService progressionService;

    @Autowired
    private ConcreteProgressionService concreteProgressionService;


    /**
     * Genera Acordes Concretos apartir de un acorde y su tonica
     * @param idChord - Acorde Abstracto
     * @param idTonic - Nota Base
     * @return Lista de Acordes Concretos
     */
    @GetMapping("/chord/concrete/{idChord}/tonic/{idTonic}")
    ResponseEntity<Optional<ConcreteChordDTO>> generateConcreteChordWithTonic(@PathVariable Long idChord, @PathVariable Long idTonic){
        List<ConcreteChord> concreteChords = concreteChordService.generateConcreteChords(chordService.getById(idChord).get(), noteService.getById(idTonic).get() );
        if( concreteChords.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        Optional<ConcreteChordDTO> concreteChordDTO = concreteChordService.concreteChordToConcreteChordDTO(concreteChords);
        if( !concreteChordDTO.isPresent() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteChordDTO, HttpStatus.OK);
    }


    /**
     * Genera Los Intervalos de un Acorde
     * @param id - ID del Acorde abstracto en la base de datos
     * @return Lista de intervalos del Acorde
     */
    @GetMapping("/interval/chord/{id}")
    ResponseEntity<List<IntervalDTO>> generateIntervalsOfChord(@PathVariable Long id){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.generateIntervalsOfChord(chord.get());
        if( intervals.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Genera los Grados de una Escala
     * @param id - ID de la escala en la base de datos
     * @return HashMap de String Grados y Chord acorde
     */
    @GetMapping("/chord/scale/{id}")
    ResponseEntity<ScaleGradesDTO> generateGradesOfScale(@PathVariable Long id){
        Optional<Scale> scale = scaleService.getById(id);
        if( !scale.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> grades = chordService.generateGradesOfScale(scale.get());
        if( grades.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( chordService.scaleGradesToScaleGradeDTO( grades ) , HttpStatus.OK);
    }

    /**
     * Genera los Grados concretos de una Escala concreta
     * @param idConcreteScale - ID de la escala en la base de datos
     * @return HashMap de String Grados y Chord acorde
     */
    @GetMapping("/chord/scale/concrete/{idConcreteScale}")
    ResponseEntity<Optional<ConcreteScaleGradesDTO>> generateConcreteGradesOfScaleWithById(@PathVariable Long idConcreteScale){
        List<ConcreteScale> scale = concreteScaleService.getCompleteConcreteScaleById( idConcreteScale );
        if( scale.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.generateConcreteGradesOfScale( scale );
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        Optional<ConcreteScaleGradesDTO> concreteScaleGradeDTO = concreteChordService.concreteScaleGradesToConcreteScaleGradesDTO(concreteScaleGrades);
        if( !concreteScaleGradeDTO.isPresent())return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteScaleGradeDTO , HttpStatus.OK);
    }



    // Progressiones
    @GetMapping("/progression/{idProgression}/scale/{idScale}/simple")
    ResponseEntity<Optional<ProgressionGradeDTO>> generateSimpleProgressionGradeOfScale(@PathVariable Long idProgression, @PathVariable Long idScale){
        Optional<Progression> progression = progressionService.getById(idProgression);
        if( !progression.isPresent())  return new ResponseEntity(new Message(-1,"Progression Not Fount"), HttpStatus.NOT_FOUND);

        Optional<Scale> scale = scaleService.getById(idScale);
        if( !scale.isPresent())  return new ResponseEntity(new Message(-1,"Scale Not Found"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale.get());
        if( scaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Scale Grades not Found"), HttpStatus.NOT_FOUND);

        List<ProgressionGrade> progressionGrades = progressionService.generateProgressionGradeSimple( progression.get(), scaleGrades );
        if( scale.isEmpty() ) return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);

        Optional<ProgressionGradeDTO>  progressionGradeDTO = progressionService.progressionGradeToProgressionGradeDTO( progressionGrades );

        return new ResponseEntity<>( progressionGradeDTO , HttpStatus.OK);
    }

    @GetMapping("/progression/{idProgression}/scale/{idScale}/force")
    ResponseEntity<Optional<ProgressionGradeDTO>> generateForceProgressionGradeOfScale(@PathVariable Long idProgression, @PathVariable Long idScale){
        Optional<Progression> progression = progressionService.getById(idProgression);
        if( !progression.isPresent())  return new ResponseEntity(new Message(-1,"Progression Not Fount"), HttpStatus.NOT_FOUND);

        Optional<Scale> scale = scaleService.getById(idScale);
        if( !scale.isPresent())  return new ResponseEntity(new Message(-1,"Scale Not Found"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale.get());
        if( scaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Scale Grades not Found"), HttpStatus.NOT_FOUND);

        List<ProgressionGrade> progressionGrades = progressionService.generateProgressionGradeForce( progression.get(), scaleGrades );
        if( scale.isEmpty() ) return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);


        return new ResponseEntity<>( progressionService.progressionGradeToProgressionGradeDTO(progressionGrades) , HttpStatus.OK);
    }

    @GetMapping("/progression/concrete/{idProgressionGrade}/s/concrete/{idConcreteScale}")
    ResponseEntity<Optional<ConcreteProgressionDTO>> generateConcreteProgressionWithTonic(@PathVariable Long idProgressionGrade, @PathVariable Long idConcreteScale){
        List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeById(idProgressionGrade);
        if( progressionGrades.isEmpty())  return new ResponseEntity(new Message(-1,"Progressions Grades Not Found"), HttpStatus.NOT_FOUND);

        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( idConcreteScale );
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Note Grades Not Found"), HttpStatus.NOT_FOUND);

        List<ConcreteProgression> concreteProgressions = concreteProgressionService.generateConcreteProgression( progressionGrades, concreteScaleGrades );
        if( concreteProgressions.isEmpty())  return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);

        Optional<ConcreteProgressionDTO> concreteProgressionDTO = concreteProgressionService.concreteProgressionToConcreteProgressionDTO(concreteProgressions);

        return new ResponseEntity<>( concreteProgressionDTO , HttpStatus.OK);
    }




    /**
     * Genera y Guarda los intervalos de todos los acordes en la base de datos
     * @return
     */
    @GetMapping("/interval/chord/save")
    ResponseEntity<Message> generateAllIntervalsOfChordAndSave(){
        intervalService.generateAllIntervalsOfChordsAndSave();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda las escalas concretas de todas las escalas abstractas en la base de datos
     * @return Mensaje de exito
     * @return
     */
    @GetMapping("/scale/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteScales(){
        concreteScaleService.generateAllConcreteScalesAndSave();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda los intervalos concretos de todos los intervalos abstractos en la base de datos
     * @return
     */
    @GetMapping("/interval/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteIntervals(){
        concreteIntervalService.generateAllConcreteIntervalsAndSave();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda los acordes concretos de todos los acordes en la base de datos
     * @return Mensaje de exito
     */
    @GetMapping("/chord/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteChords(){
        concreteChordService.generateAndSaveAllConcretechords();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda los grados de la escala de todas las escalas en la base de datos
     * @return Mensaje de exito
     */
    @GetMapping("/chord/scale/save")
    ResponseEntity<Message> generateAndSaveAllGradesOfScales(){
        chordService.generateAllGradesOfScaleAndSave();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }

    /**
     * Genera y Guarda los grados de la escala de todas las escalas en la base de datos
     * @return Mensaje de exito
     */
    @GetMapping("/chord/scale/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteGradesOfScales(){
        concreteChordService.generateAndSaveAllConcreteGradesOfScale();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }


    @GetMapping("/progression/scale/save")
    ResponseEntity<Message> generateAndSaveAllProgressionsSimpleOfScale(){
        progressionService.generateAndSaveAllProgresionGradeSimple();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }

    @GetMapping("/progression/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteProgressions(){
        concreteProgressionService.generateAndSaveAllConcreteProgressions();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }
}
