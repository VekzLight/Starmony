package com.kadli.starmony.service;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ConcreteChordRepository;
import com.kadli.starmony.repository.ConcreteProgressionRepository;
import com.kadli.starmony.repository.ConcreteScaleGradeRepository;
import com.kadli.starmony.utilities.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private ConcreteProgressionRepository concreteProgressionRepository;

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
            concreteChordIdTonic.setPosition_note_chord(1);
            concreteChordIdTonic.setId_concrete_chord(idConcreteChord);

            ConcreteChord concreteChordTonic = new ConcreteChord();
            concreteChordTonic.setConcreteChord(chord);
            concreteChordTonic.setNote(concreteInterval.getFirstNote());
            concreteChordTonic.setCc_id(concreteChordIdTonic);

            ConcreteChordId concreteChordId = new ConcreteChordId();
            concreteChordId.setPosition_note_chord(position);
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
    public List<ConcreteChordDTO> getConcreteChordsWithNotesAndTonic(List<Long> idNotes, Long tonic) {
        List<Long> ids = concreteChordRepository.getIdConcreteChordsWithTonic(tonic);
        List<ConcreteChordDTO> concreteChordsDTO = new ArrayList<>();
        for(Long id: ids){
            List<ConcreteChord> concreteChordsIt = concreteChordRepository.getConcreteChordsByIdConcrete(id);

            int count = 0;
            for(ConcreteChord it: concreteChordsIt){
                if( idNotes.contains(it.getNote().getId()) ) count++;
            }

            if(idNotes.size() == count){
                concreteChordsDTO.add( this.concreteChordToConcreteChordDTO(concreteChordsIt).get() );
            }
        }
        return concreteChordsDTO;
    }


    @Override
    public HashMap<Long, List<ConcreteScaleGrade>> generateConcreteGradesOfScales(List<ConcreteScale> concreteScales, HashMap<Long, List<ScaleGrade>> scaleGrades) {
        HashMap<Long, List<ConcreteScaleGrade>> concreteScaleGrades = new HashMap<>();

        // Ordena las notas de la escala por posicion
        HashMap<Integer, ConcreteScale> concreteScaleHashMap = new HashMap<>();
        for(ConcreteScale concreteScale: concreteScales){
            concreteScaleHashMap.put( concreteScale.getId().getPosition(), concreteScale );
        }

        // Nota tonica
        Note tonic = concreteScaleHashMap.get(1).getNotesOfScale();


        // Obtiene Los grados de la escala para las triadas, septimas y novenas
        Long idConcreteScaleGrade = this.getLastConcreteGradeScaleId() + (tonic.getId() * 3) + 1;
        for(Long key:  scaleGrades.keySet()){
            // Siguiente Id de la escala concreta
            List<ConcreteScaleGrade> concreteScaleGradesBuffer = new ArrayList<>();
            for(ScaleGrade scaleGrade: scaleGrades.get(key)){
                if(scaleGrade.isExist()){
                    int positionGrade = Symbols.GRADE_TO_POS( scaleGrade.getId().getGrade() );

                    ConcreteScale concreteScale = concreteScaleHashMap.get(positionGrade);
                    Chord chord = scaleGrade.getChordOfScale();
                    List<ConcreteChord> concreteChordsList = this.getCompleteConcreteChordWithTonic(chord.getId(), concreteScale.getNotesOfScale().getId());

                    for(ConcreteChord concretechord: concreteChordsList){
                        ConcreteScaleGradeId concreteScaleGradeId = new ConcreteScaleGradeId();
                        concreteScaleGradeId.setId_concrete_scale_grade( idConcreteScaleGrade );
                        concreteScaleGradeId.setId_concrete_scale( concreteScale.getId().getId_concrete_scale() );
                        concreteScaleGradeId.setPosition_note_scale( concreteScale.getId().getPosition() );
                        concreteScaleGradeId.setId_scale_grade( scaleGrade.getId().getId_scale_grade() );
                        concreteScaleGradeId.setGrade( scaleGrade.getId().getGrade() );
                        concreteScaleGradeId.setId_concrete_chord( concretechord.getCc_id().getId_concrete_chord() );
                        concreteScaleGradeId.setPosition_note_chord( concretechord.getCc_id().getPosition_note_chord() );

                        ConcreteScaleGrade concreteScaleGrade = new ConcreteScaleGrade();
                        concreteScaleGrade.setId( concreteScaleGradeId );
                        concreteScaleGrade.setScaleGrade( scaleGrade );
                        concreteScaleGrade.setConcreteScale( concreteScale );
                        concreteScaleGrade.setConcreteChord(concretechord);

                        concreteScaleGradesBuffer.add(concreteScaleGrade);
                    }
                } else {
                    concreteScaleGradesBuffer.add( Symbols.concreteScaleGrade );
                }
            }
            concreteScaleGrades.put(idConcreteScaleGrade, concreteScaleGradesBuffer);
            idConcreteScaleGrade++;
        }

        return concreteScaleGrades;
    }

    @Override
    public List<ConcreteScaleGrade> generateConcreteGradesOfScale(List<ConcreteScale> concreteScales, HashMap<Long, List<ScaleGrade>> scaleGrades) {
        List<ConcreteScaleGrade> concreteScaleGrades = new ArrayList<>();

        HashMap<Integer, ConcreteScale> concreteScaleHashMap = new HashMap<>();
        for(ConcreteScale concreteScale: concreteScales)
            concreteScaleHashMap.put( concreteScale.getId().getPosition(), concreteScale );

        Long id = this.getLastConcreteGradeScaleId() + 1;
        for(List<ScaleGrade> scaleGradesIt:  scaleGrades.values()){
            for(ScaleGrade scaleGrade: scaleGradesIt){
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
                    concreteScaleGradeId.setPosition_note_chord( concretechord.getCc_id().getPosition_note_chord() );

                    ConcreteScaleGrade concreteScaleGrade = new ConcreteScaleGrade();
                    concreteScaleGrade.setId( concreteScaleGradeId );
                    concreteScaleGrade.setScaleGrade( scaleGrade );
                    concreteScaleGrade.setConcreteScale( concreteScale );
                    concreteScaleGrade.setConcreteChord(concretechord);

                    concreteScaleGrades.add(concreteScaleGrade);
                }
            }

        }

        return concreteScaleGrades;
    }

    @Override
    public List<ConcreteScaleGrade> generateAndSaveConcreteGradesOfScale(List<ConcreteScale> concreteScales, HashMap<Long, List<ScaleGrade>> scaleGrades) {
        List<ConcreteScaleGrade> concreteScaleGrades = this.generateConcreteGradesOfScale(concreteScales, scaleGrades);
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

                HashMap<Long, List<ScaleGrade>> scaleGrades = chordService.generateGradesOfScale(scale);

                List<ConcreteScaleGrade> generated = this.generateAndSaveConcreteGradesOfScale(concreteScales,scaleGrades );
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

    @Override
    public List<Long> getAllIdConcreteChords() {
        return concreteChordRepository.getAllIdConcreteChords();
    }


    // Conversiones Concretas
    @Override
    public Optional<ConcreteChordDTO> concreteChordToConcreteChordDTO(List<ConcreteChord> concreteChords) {
        ConcreteChordDTO concreteChordDTO = new ConcreteChordDTO();
        List<NoteDTO> notes = new ArrayList<>();

        for (ConcreteChord concreteChord : concreteChords) {
            notes.add(this.noteService.entityToDTO(concreteChord.getNote()));
            if (concreteChord.getCc_id().getPosition_note_chord() == 1) {
                concreteChordDTO.setTonic(this.noteService.entityToDTO(concreteChord.getNote()));
                concreteChordDTO.setCode(concreteChord.getConcreteChord().getCode());
                concreteChordDTO.setId(concreteChord.getConcreteChord().getId());
                concreteChordDTO.setName(concreteChord.getConcreteChord().getName());
                concreteChordDTO.setSymbol(concreteChord.getConcreteChord().getSymbol());
                concreteChordDTO.setId_concrete_chord( concreteChord.getCc_id().getId_concrete_chord() );
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
        concreteScaleGradeDTO.setId( scale != null ? scale.getId(): -1 );
        concreteScaleGradeDTO.setName( scale != null ?scale.getName():"" );
        concreteScaleGradeDTO.setSymbol( scale != null ?scale.getSymbol():"" );
        concreteScaleGradeDTO.setCode(scale != null ?scale.getCode():"" );

        // Id de los grados de la escala
        concreteScaleGradeDTO.setIdConcreteScaleGrade( concreteScaleGrade.getId() != null ? concreteScaleGrade.getId().getId_concrete_scale_grade() : -1);
        concreteScaleGradeDTO.setIdScaleGrade( concreteScaleGrade.getId() != null ? concreteScaleGrade.getId().getId_scale_grade() :-1);

        HashMap<Integer,ConcreteChordDTO> concreteChordsDTO = new HashMap<>();
        HashMap<Integer,ChordDTO> chordsDTO = new HashMap<>();

        List<ConcreteChord> concreteChords = new ArrayList<>();

        Long positionActual = -1L;
        String gradeActual = "0";
        Long idConcreteChord = -1L;
        for(ConcreteScaleGrade it:concreteScaleGrades){
            if(positionActual == -1) {
                if(it.getId() != null){
                    positionActual = it.getId().getId_concrete_chord();
                    gradeActual = it.getId().getGrade();
                } else {
                    positionActual = -1L;
                    gradeActual  = "0";
                }
            }

            if(it.getId() != null){
                if(positionActual != it.getId().getId_concrete_chord()) {

                    List<ConcreteChord> cc = this.getCompleteConcreteChordById(positionActual);
                    Optional<ConcreteChordDTO> ccDTO = this.concreteChordToConcreteChordDTO(cc);
                    if (!ccDTO.isPresent()) return Optional.empty();
                    concreteChordsDTO.put(Symbols.GRADE_TO_POS(gradeActual), ccDTO.get());

                    positionActual = it.getId().getId_concrete_chord();
                    gradeActual = it.getId().getGrade();
                    concreteChords.clear();
                }

                Chord chord = it.getScaleGrade().getChordOfScale();
                ChordDTO chordDTO = chordService.entityToDTO( chord );
                chordsDTO.put( Symbols.GRADE_TO_POS(it.getId().getGrade()) ,chordDTO );
            } else {
                concreteChordsDTO.put( -1, new ConcreteChordDTO() );
                chordsDTO.put(-1, new ChordDTO());
            }


        }

        List<ConcreteChord> cc = this.getCompleteConcreteChordById(positionActual);
        Optional<ConcreteChordDTO> ccDTO = this.concreteChordToConcreteChordDTO( cc );
        if( !ccDTO.isPresent() ) return Optional.empty();
        concreteChordsDTO.put( Symbols.GRADE_TO_POS(gradeActual), ccDTO.get() );

        concreteScaleGradeDTO.setConcreteGrades( concreteChordsDTO );
        concreteScaleGradeDTO.setGrades( chordsDTO );

        return Optional.of(concreteScaleGradeDTO);
    }

    @Override
    public Long getLastConcreteGradeScaleId() {
        Long id = concreteScaleGradeRepository.getLastId();
        return id == null ? 0 : id;
    }

    @Override
    public List<Long> getConcreteScaleGradeIdWithConcreteChordId(Long concreteChordId) {
        return concreteScaleGradeRepository.getConcreteScaleGradeIdWithConcreteChordId(concreteChordId);
    }

    @Override
    public HashMap<Long, List<Integer>> getConcreteProgressionsIdWithConcreteChordId(Long concreteChordId) {
        List<ConcreteProgressionId> concreteProgressionCustomQueries = concreteProgressionRepository.getConcreteProgressionIdWithConcreteChordId(concreteChordId);

        HashMap<Long,List<Integer>> concreteProgressionsIds = new HashMap<>();
        for(ConcreteProgressionId set: concreteProgressionCustomQueries){
            if(concreteProgressionsIds.containsKey( set.getId_concrete_progression())){
                List<Integer> ids = concreteProgressionsIds.get(set.getId_concrete_progression());
                ids.add( set.getPosition_concrete_chord() );
                concreteProgressionsIds.put(set.getId_concrete_progression(), ids);
            }
            else{
                List<Integer> ids = new ArrayList<>();
                ids.add(set.getPosition_concrete_chord());
                concreteProgressionsIds.put(set.getId_concrete_progression(), ids);
            }
        }
        return concreteProgressionsIds;
    }

    @Override
    public List<ConcreteScaleGrade> getCompleteConcreteScaleGradeById(Long id) {
        return concreteScaleGradeRepository.getCompleteConcreteScaleGradeByCSGId(id);
    }

    @Override
    public Chord getIdChordOfIdConcreteChord(Long concreteChordId) {
        return concreteChordRepository.getChordOfConcreteChordId(concreteChordId);
    }

    @Override
    public List<Long> getAllIdConcreteScaleGrades() {
        return concreteScaleGradeRepository.getAllConcreteScaleGradesIds();
    }

    @Override
    public Long getIdConcreteScaleWithScale(Long idConcreteScale) {
        return concreteScaleGradeRepository.getConcreteScaleGradeIdWithScaleAndTonicId(idConcreteScale);
    }

    @Override
    public List<Long> getConcreteChordsIdByConcreteScaleId(Long concreteScaleId) {
        return concreteScaleGradeRepository.getConcreteChordsOfConcreteScale(concreteScaleId);
    }


}
