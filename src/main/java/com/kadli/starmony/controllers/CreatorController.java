package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.Message;
import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.ConcreteScale;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.entity.ScaleGrade;
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
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteScaleService concreteScaleService;


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
        System.out.println(scaleGrades.size() + " " + scale.getCode().split(Symbols.SYMBOL_SEPARATION_SCALE).length);
        return scaleGrades.size() == scale.getCode().split(Symbols.SYMBOL_SEPARATION_SCALE).length;
    }

    private boolean postCreatedScale(Scale scale){
        boolean probe1 = createConcreteScales(scale);
        boolean probe2 = createGradeScale(scale);
        return probe1 && probe2;
    }

    @PostMapping(path = "/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ScaleDTO>> createNewScale(@RequestBody ScaleDTO scaleDTO){
        Optional<Scale> scale = Optional.of(scaleService.dtotoEntity(scaleDTO));
        if( scaleService.exist( scale.get() ) ) {
            scale = scaleService.get( scale.get() );
            return new ResponseEntity<>( Optional.of(scaleService.entityToDTO( scale.get() )) , HttpStatus.OK );
        }

        if( scale.get().getCode() == null &&
                scale.get().getName() == null &&
                scale.get().getSymbol() == null)
            return new ResponseEntity( new Message(-1, "Escala no valida"), HttpStatus.NOT_ACCEPTABLE );

        scaleService.save(scale.get());

        Optional<Scale> response = scaleService.get( scale.get() );
        if( !response.isPresent() )  return new ResponseEntity( new Message(-1, "Escala no guardada"), HttpStatus.NOT_FOUND );
        if( !this.postCreatedScale( response.get() ) ) return new ResponseEntity( new Message(-1, "Escala no relevante"), HttpStatus.NOT_ACCEPTABLE );

        return new ResponseEntity<>( Optional.of( scaleService.entityToDTO(response.get())),HttpStatus.OK);
    }



}
