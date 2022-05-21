package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.Message;
import com.kadli.starmony.dto.ProgressionDTO;
import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import com.kadli.starmony.utilities.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creator")
@CrossOrigin(origins = "http://localhost:4200")
public class CreatorController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private ChordService chordService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ProgressionService progressionService;

    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ConcreteProgressionService concreteProgressionService;

    private boolean createConcreteScales(Scale scale){
        List<Note> notes = noteService.getAll();
        List<ConcreteScale> concreteScales = new ArrayList<>();
        for(Note note: notes)
            concreteScales.addAll(concreteScaleService.generateCompleteConcreteScalesAndSave( scale, note));

        int codeLenght = scale.getCode().split(Symbols.SYMBOL_SEPARATION_SCALE).length + 1;
        System.out.println(codeLenght + " " + concreteScales.size());
        return codeLenght * notes.size() == concreteScales.size();
    }

    private boolean createGradeScale(Scale scale){
        List<ScaleGrade> scaleGrades = chordService.generateGradesOfScaleAndSave( scale );
        return scaleGrades.size() == scale.getCode().split(Symbols.SYMBOL_SEPARATION_SCALE).length;
    }


    private boolean createCocnreteScaleGrades(Scale scale) {
        List<Note> notes = noteService.getAll();
        for(Note note: notes){
            List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), note.getId());
            List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.generateAndSaveConcreteGradesOfScale( concreteScales );
            if(concreteScaleGrades.isEmpty()) return false;
        }
        return true;
    }

    private boolean postCreatedScale(Scale scale){
        boolean probe1 = createConcreteScales(scale);
        System.out.println( "concreteScale: " + probe1 );
        boolean probe2 = createGradeScale(scale);
        System.out.println( "ScaleGrades: " + probe2 );
        boolean probe3 = createCocnreteScaleGrades(scale);
        System.out.println( "ConcreteScaleGrade: " + probe3 );
        createProgressionsOfScales(scale);
        return probe1 && probe2 && probe3;
    }

    private boolean createProgressionsOfScales(Scale scale) {
        List<Note> notes = noteService.getAll();
        List<Progression> progressions = progressionService.getAll();

        List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale);
        for (Progression progression: progressions)
            progressionService.generateAndSaveProgressionGradeSimple( progression, scaleGrades );

        for(Note note: notes){
            Long idConcreteScale = concreteScaleService.getIdConcreteScale(scale.getId(), note.getId());
            List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( idConcreteScale );

            for(Progression progression: progressions){
                List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeByScaleGrade(progression.getId(), scaleGrades.get(0).getId().getId_scale_grade());
                concreteProgressionService.generateAndSaveConcreteProgression(progressionGrades, concreteScaleGrades, idConcreteScale);
            }
        }
        return true;
    }


    private boolean createConcreteProgressions(Progression progression) {
        List<Scale> scales =scaleService.getAll();
        List<Note> notes = noteService.getAll();

        boolean flag = false;
        for(Scale scale:scales){
            for (Note note: notes){
                Long concreteScaleId = concreteScaleService.getIdConcreteScale(scale.getId(), note.getId());
                List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( concreteScaleId );

                Long scaleGradeId = chordService.getIdScaleGrade(scale);
                List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeByScaleGrade(progression.getId(), scaleGradeId);

                List<ConcreteProgression> concreteProgressions = concreteProgressionService.generateAndSaveConcreteProgression(progressionGrades, concreteScaleGrades, concreteScaleId );
                if(!concreteProgressions.isEmpty())  flag = true;
            }
        }
        return flag;
    }

    private boolean createProgressionGrades(Progression progression) {
        List<Scale> scales = scaleService.getAll();
        boolean flag = false;
        for( Scale scale: scales ){
            List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale);
            List<ProgressionGrade> progressionGrades = progressionService.generateAndSaveProgressionGradeSimple( progression, scaleGrades );
            if(!progressionGrades.isEmpty()) flag = true;
        }
        return flag;
    }

    private boolean postCreatedProgression(Progression progression) {
        boolean probe1 = createProgressionGrades(progression);
        System.out.println("Progression Grades " + probe1);
        boolean probe2 = createConcreteProgressions(progression);
        System.out.println("ConcreteProgressions " + probe2);
        return probe1 && probe2;
    }

    @PostMapping(path = "/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewScale(@RequestBody ScaleDTO scaleDTO){
        scaleDTO.setCode(scaleDTO.getCode().replaceAll("-", Symbols.SYMBOL_SEPARATION_SCALE));
        Optional<Scale> scale = Optional.of(scaleService.dtotoEntity(scaleDTO));
        if( scaleService.exist( scale.get() ) ) {
            scale = scaleService.get( scale.get() );
            return new ResponseEntity<>( new Message( 2, scaleService.entityToDTO(scale.get()).getId() + "") , HttpStatus.OK );
        }

        if( scale.get().getCode() == null &&
                scale.get().getName() == null &&
                scale.get().getSymbol() == null)
            return new ResponseEntity( new Message(-1, "Escala no valida"), HttpStatus.OK );

        scaleService.save(scale.get());

        Optional<Scale> response = scaleService.get( scale.get() );
        if( !response.isPresent() )  return new ResponseEntity( new Message(-1, "Escala no guardada"), HttpStatus.OK );
        if( !this.postCreatedScale( response.get() ) ) {
            scaleService.delete(scale.get());
            return new ResponseEntity( new Message(-1, "Escala no relevante"), HttpStatus.OK );
        }

        return new ResponseEntity<>( new Message( 1, scaleService.entityToDTO(response.get()).getId() + ""),HttpStatus.OK);
    }

    @PostMapping(path = "/progression",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewProgression(@RequestBody ProgressionDTO progressionDTO){
        Optional<Progression> progression = Optional.of(progressionService.dtotoEntity(progressionDTO));
        if( progressionService.exist( progression.get() ) ) {
            progression = progressionService.get( progression.get() );
            return new ResponseEntity<>( new Message( 1, progression.get().getId() + ""),HttpStatus.OK);
        }

        if( progression.get().getCode() == null &&
                progression.get().getName() == null &&
                progression.get().getSymbol() == null)
            return new ResponseEntity( new Message(-1, "Progression no valida"), HttpStatus.NOT_ACCEPTABLE );

        progressionService.save(progression.get());

        Optional<Progression> response = progressionService.get( progression.get() );
        if( !response.isPresent() )  return new ResponseEntity( new Message(-1, "Progression no guardada"), HttpStatus.NOT_FOUND );
        if( !this.postCreatedProgression( response.get() ) ) return new ResponseEntity( new Message(-1, "Progression no relevante"), HttpStatus.NOT_ACCEPTABLE );


        return new ResponseEntity<>( new Message( 1, response.get().getId() + ""),HttpStatus.OK);
    }


}
