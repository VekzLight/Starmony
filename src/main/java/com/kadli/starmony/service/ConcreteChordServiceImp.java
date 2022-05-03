package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.ConcreteScaleGradesDTO;
import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ConcreteChordRepository;
import com.kadli.starmony.repository.ConcreteScaleGradeRepository;
import com.kadli.starmony.utilities.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ConcreteChordService")
public class ConcreteChordServiceImp implements ConcreteChordService{

    @Autowired
    private ConcreteChordRepository concreteChordRepository;

    @Autowired
    private ChordService chordService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ConcreteScaleGradeRepository concreteScaleGradeRepository;

    @Autowired
    private ConcreteScaleService concreteScaleService;


    // Obtener
    @Override
    public List<ConcreteChord> getAllConcreteChords() {
        return concreteChordRepository.findAll();
    }

    @Override
    public List<ConcreteChord> getCompleteConcreteChordById(Long id) {
        return concreteChordRepository.getConcreteChordsByIdConcrete( id );
    }

    @Override
    public List<ConcreteChord> getCompleteConcreteChordWithTonic(Long idChord, Long idTonic) {
        Long idConcrete = concreteChordRepository.getIdConcreteChord(idChord, idTonic);
        if (idConcrete == null) return new ArrayList<>();
        return this.getCompleteConcreteChordById( idConcrete );
    }

    @Override
    public List<ConcreteScaleGrade> getCompleteConcreteScaleGradesByConcreteScaleId(Long idConcrete) {
        List<ConcreteScaleGrade> concreteScaleGrades = concreteScaleGradeRepository.getCompleteConcreteScaleGradeByCSGId(idConcrete);
        if (concreteScaleGrades.isEmpty()) return new ArrayList<>();
        return concreteScaleGrades;
    }

    // Generadores
    @Override
    public List<ConcreteChord> generateConcreteChords(Chord chord, Note tonic) {

        // Comprobaciones de que existan los elementos dados
        if (!chordService.exist(chord)) {
            return null;
        }
        if (!noteService.exist(tonic)) {
            return null;
        }

        // Obtiene los intervalos del acorde
        List<Interval> intervals = intervalService.generateIntervalsOfChord(chord);

        // Obtiene los Intervalos concretos que contengan la tonica dada
        List<ConcreteInterval> concreteIntervals = intervals.stream().flatMap((interval) -> {
            List<ConcreteInterval> response = new ArrayList<>();
            for (ConcreteInterval cci : interval.getConcreteIntervals())
                if (cci.getFirstNote() == tonic)
                    response.add(cci);

            return response.stream();
        }).collect(Collectors.toList());


        List<ConcreteChord> concreteChords = new ArrayList<>();
        Long idConcreteChord = this.getLastConcreteChordId() + 1;
        int position = 2;

        for (ConcreteInterval concreteInterval : concreteIntervals) {
            ConcreteChordId concreteChordIdTonic = new ConcreteChordId();
            concreteChordIdTonic.setPosition(1);
            concreteChordIdTonic.setId_concrete_chord(idConcreteChord);

            ConcreteChord concreteChordTonic = new ConcreteChord();
            concreteChordTonic.setConcreteChord(chord);
            concreteChordTonic.setNote(concreteInterval.getFirstNote());
            concreteChordTonic.setCc_id(concreteChordIdTonic);

            ConcreteChordId concreteChordId = new ConcreteChordId();
            concreteChordId.setPosition(position);
            concreteChordId.setId_concrete_chord(idConcreteChord);

            ConcreteChord concreteChord = new ConcreteChord();
            concreteChord.setConcreteChord(chord);
            concreteChord.setNote(concreteInterval.getLastNote());
            concreteChord.setCc_id(concreteChordId);

            concreteChords.add(concreteChordTonic);
            concreteChords.add(concreteChord);

            position++;
        }
        return concreteChords;
    }




    @Override
    public List<ConcreteChord> generateAndSaveConcreteChords(Chord chord, Note tonic) {
        List<ConcreteChord> concreteChords = this.generateConcreteChords(chord, tonic);
        concreteChordRepository.saveAll(concreteChords);
        return concreteChords;
    }




    @Override
    public List<ConcreteChord> generateAndSaveAllConcretechords() {
        List<ConcreteChord> concreteChords = new ArrayList<>();
        List<Chord> chords = chordService.getAll();
        List<Note> notes = noteService.getAll();
        for (Chord chord : chords) {
            for (Note note : notes) {
                concreteChords.addAll(this.generateAndSaveConcreteChords(chord, note));
            }
        }
        return concreteChords;
    }




    @Override
    public List<ConcreteScaleGrade> generateConcreteGradesOfScale(List<ConcreteScale> concreteScales) {
        List<ConcreteScaleGrade> concreteScaleGrades = new ArrayList<>();

        HashMap<Integer, ConcreteScale> concreteScaleHashMap = new HashMap<>();
        for(ConcreteScale concreteScale: concreteScales)
            concreteScaleHashMap.put( concreteScale.getId().getPosition(), concreteScale );

        Long id = this.getLastConcreteGradeScaleId() + 1;
        for(ScaleGrade scaleGrade: chordService.getGradesOfScale( concreteScales.get(0).getScaleOfNotes() ) ){

            int positionGrade = Symbols.GRADE_TO_POS( scaleGrade.getId().getGrade() );

            ConcreteScale concreteScale = concreteScaleHashMap.get(positionGrade);
            Chord chord = scaleGrade.getChordOfScale();
            List<ConcreteChord> concreteChordsList = this.getCompleteConcreteChordWithTonic(chord.getId(), concreteScale.getNotesOfScale().getId());
            for(ConcreteChord concretechord: concreteChordsList){
                ConcreteScaleGradeId concreteScaleGradeId = new ConcreteScaleGradeId();
                concreteScaleGradeId.setId_concrete_scale_grade( id );
                concreteScaleGradeId.setId_concrete_scale( concreteScale.getId().getId_concrete_scale() );
                concreteScaleGradeId.setPosition_note_scale( concreteScale.getId().getPosition() );
                concreteScaleGradeId.setId_scale_grade( scaleGrade.getId().getId_scale_grade() );
                concreteScaleGradeId.setGrade( scaleGrade.getId().getGrade() );
                concreteScaleGradeId.setId_concrete_chord( concretechord.getCc_id().getId_concrete_chord() );
                concreteScaleGradeId.setPosition_note_chord( concretechord.getCc_id().getPosition() );

                ConcreteScaleGrade concreteScaleGrade = new ConcreteScaleGrade();
                concreteScaleGrade.setId( concreteScaleGradeId );
                concreteScaleGrade.setScaleGrade( scaleGrade );
                concreteScaleGrade.setConcreteScale( concreteScale );
                concreteScaleGrade.setConcreteChord(concretechord);

                concreteScaleGrades.add(concreteScaleGrade);
            }
        }

        return concreteScaleGrades;
    }

    @Override
    public List<ConcreteScaleGrade> generateAndSaveConcreteGradesOfScale(List<ConcreteScale> concreteScales) {
        List<ConcreteScaleGrade> concreteScaleGrades = this.generateConcreteGradesOfScale(concreteScales);
        if(concreteScaleGrades.isEmpty()) return new ArrayList<>();
        concreteScaleGradeRepository.saveAll(concreteScaleGrades);
        return concreteScaleGrades;
    }

    @Override
    public List<ConcreteScaleGrade> generateAndSaveAllConcreteGradesOfScale() {
        List<Scale> scales = scaleService.getAll();
        List<Note> notes = noteService.getAll();

        List<ConcreteScaleGrade> concreteScaleGrades = new ArrayList<>();
        for(Scale scale: scales){
            for(Note note: notes){
                List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleWithTonic( scale.getId(), note.getId());
                if(concreteScales.isEmpty()) continue;
                List<ConcreteScaleGrade> generated = this.generateAndSaveConcreteGradesOfScale(concreteScales);
                if(generated.isEmpty())
                concreteScaleGrades.addAll( generated );
            }
        }
        return concreteScaleGrades;
    }


    // Utilidades
    @Override
    public Long getLastConcreteChordId() {
        Long id = concreteChordRepository.getMaxId();
        return id == null ? 0 : id;
    }

    @Override
    public Long getIdConcreteChord(Long idChord, Long idTonic) {
        return concreteChordRepository.getIdConcreteChord(idChord, idTonic);
    }





    // Conversiones Concretas
    @Override
    public Optional<ConcreteChordDTO> concreteChordToConcreteChordDTO(List<ConcreteChord> concreteChords) {
        ConcreteChordDTO concreteChordDTO = new ConcreteChordDTO();
        List<NoteDTO> notes = new ArrayList<>();

        for (ConcreteChord concreteChord : concreteChords) {
            notes.add(this.noteService.entityToDTO(concreteChord.getNote()));
            if (concreteChord.getCc_id().getPosition() == 1) {
                concreteChordDTO.setTonic(this.noteService.entityToDTO(concreteChord.getNote()));
                concreteChordDTO.setCode(concreteChord.getConcreteChord().getCode());
                concreteChordDTO.setId(concreteChord.getConcreteChord().getId());
                concreteChordDTO.setName(concreteChord.getConcreteChord().getName());
                concreteChordDTO.setSymbol(concreteChord.getConcreteChord().getSymbol());
            }
        }
        concreteChordDTO.setNotes(notes);
        return Optional.of(concreteChordDTO);
    }

    @Override
    public Optional<ConcreteScaleGradesDTO> concreteScaleGradesToConcreteScaleGradesDTO(List<ConcreteScaleGrade> concreteScaleGrades) {

        ConcreteScaleGrade concreteScaleGrade = concreteScaleGrades.get(0);
        Scale scale = concreteScaleGrades.get(0).getScaleGrade().getScaleOfChord();

        ConcreteScaleGradesDTO concreteScaleGradeDTO = new ConcreteScaleGradesDTO();

        // Informacion de la Escale
        concreteScaleGradeDTO.setId( scale.getId() );
        concreteScaleGradeDTO.setName( scale.getName() );
        concreteScaleGradeDTO.setSymbol( scale.getSymbol() );
        concreteScaleGradeDTO.setCode(scale.getCode() );

        // Id de los grados de la escala
        concreteScaleGradeDTO.setIdConcreteScaleGrade( concreteScaleGrade.getId().getId_concrete_scale_grade() );
        concreteScaleGradeDTO.setIdScaleGrade( concreteScaleGrade.getId().getId_scale_grade() );

        HashMap<String,ConcreteChordDTO> concreteChordsDTO = new HashMap<>();
        HashMap<String,ChordDTO> chordsDTO = new HashMap<>();

        List<ConcreteChord> concreteChords = new ArrayList<>();

        Long positionActual = -1L;
        String gradeActual = "0";
        for(ConcreteScaleGrade it:concreteScaleGrades){
            if(positionActual == -1) {
                positionActual = it.getId().getId_concrete_chord();
                gradeActual = it.getId().getGrade();
            }

            if(positionActual != it.getId().getId_concrete_chord()){
                Optional<ConcreteChordDTO> cc = this.concreteChordToConcreteChordDTO( concreteChords );
                if( !cc.isPresent() ) return Optional.empty();
                concreteChordsDTO.put( gradeActual, cc.get() );

                System.out.println("Entro " + cc.get().getName());
                positionActual = it.getId().getId_concrete_chord();
                gradeActual = it.getId().getGrade();
                concreteChords.clear();
            }
            concreteChords.add( it.getConcreteChord() );
            System.out.println("fuera " + it.getConcreteChord().getCc_id().getPosition());


            Chord chord = it.getScaleGrade().getChordOfScale();
            ChordDTO chordDTO = chordService.entityToDTO( chord );
            chordsDTO.put( it.getId().getGrade() ,chordDTO );
        }

        Optional<ConcreteChordDTO> cc = this.concreteChordToConcreteChordDTO( concreteChords );
        if( !cc.isPresent() ) return Optional.empty();
        concreteChordsDTO.put( gradeActual, cc.get() );

        concreteScaleGradeDTO.setConcreteGrades( concreteChordsDTO );
        concreteScaleGradeDTO.setGrades( chordsDTO );

        return Optional.of(concreteScaleGradeDTO);
    }

    @Override
    public Long getLastConcreteGradeScaleId() {
        Long id = concreteScaleGradeRepository.getLastId();
        return id == null ? 0 : id;
    }

}
