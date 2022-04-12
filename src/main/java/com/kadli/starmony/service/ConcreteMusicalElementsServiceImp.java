package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.ConcreteIntervalDTO;
import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ConcreteChordRepository;
import com.kadli.starmony.repository.ConcreteIntervalRepository;
import com.kadli.starmony.repository.ConcreteScaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ConcreteMusicalElementsService")
public class ConcreteMusicalElementsServiceImp implements ConcreteMusicalElementsService {

    // Servicios
    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private ScaleService scaleService;

    // Repositorios
    @Autowired
    private ConcreteChordRepository concreteChordRepository;

    @Autowired
    private ConcreteIntervalRepository concreteIntervalRepository;

    @Autowired
    private ConcreteScaleRepository concreteScaleRepository;

    // Utilidades
    @Autowired
    private ModelMapper modelMapper;


    // Obtencion de Acordes concretos
    @Override
    public List<ConcreteChord> getAllConcreteChords() {
        return concreteChordRepository.findAll();
    }

    @Override
    public List<ConcreteChord> getConcreteChordWithTonic(Chord chord, Note tonic) {
        Long idConcrete = concreteChordRepository.getIdConcreteChord(chord.getId(), tonic.getId());
        if (idConcrete == null) return new ArrayList<>();
        return concreteChordRepository.getConcreteChordsByIdConcrete(idConcrete);
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
            concreteChordIdTonic.setId_chord(chord.getId());
            concreteChordIdTonic.setId_note(concreteInterval.getFirstNote().getId());
            concreteChordIdTonic.setPosition(1);
            concreteChordIdTonic.setId_concrete_chord(idConcreteChord);

            ConcreteChord concreteChordTonic = new ConcreteChord();
            concreteChordTonic.setConcreteChord(chord);
            concreteChordTonic.setNote(concreteInterval.getFirstNote());
            concreteChordTonic.setCc_id(concreteChordIdTonic);

            ConcreteChordId concreteChordId = new ConcreteChordId();
            concreteChordId.setId_chord(chord.getId());
            concreteChordId.setId_note(concreteInterval.getLastNote().getId());
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





    // Utilidades
    @Override
    public Long getLastConcreteChordId() {
        Long id = concreteChordRepository.getMaxId();
        return id == null ? 0 : id;
    }






    // Conversiones Concretas
    @Override
    public ConcreteChordDTO concreteChordToConcreteChordDTO(List<ConcreteChord> concreteChords) {
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
        return concreteChordDTO;
    }








    // Intervalos


    // Utilidad
    private Long getNoteWithSemitone(Long tonic, int semitones){
        Long id = tonic + semitones;
        while( id > 12 ) id = id - 12;
        return id;
    }





    // Obtener
    @Override
    public ConcreteInterval getConcreteInterval(Interval interval, Note tonic, Note note) {
        return concreteIntervalRepository.getConcreteInterval(interval.getId(),tonic.getId(), note.getId() ).get();
    }

    @Override
    public List<ConcreteInterval> getConcreteIntervals(List<Interval> intervals, Note tonic, Note note){
        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        for(Interval interval: intervals)
            concreteIntervals.add( this.getConcreteInterval(interval, tonic, note) );
        return concreteIntervals;
    }




    // Generadores
    @Override
    public ConcreteInterval generateConcreteInterval(Interval interval, Note tonic) {
        int semitones = interval.getSemitones();
        Optional<Note> note = noteService.getById( this.getNoteWithSemitone(tonic.getId(), semitones) );

        if( !note.isPresent() ) return null;

        ConcreteIntervalId concreteIntervalId = new ConcreteIntervalId();
        concreteIntervalId.setId_interval(interval.getId());
        concreteIntervalId.setId_note1(tonic.getId());
        concreteIntervalId.setId_note2(note.get().getId());

        ConcreteInterval concreteInterval = new ConcreteInterval();
        concreteInterval.setId(concreteIntervalId);
        concreteInterval.setIntervalOfNotes(interval);
        concreteInterval.setFirstNote(tonic);
        concreteInterval.setLastNote(note.get());

        return concreteInterval;
    }

    @Override
    public ConcreteInterval generateConcreteIntervalAndSave(Interval interval, Note tonic) {
        ConcreteInterval concreteInterval = this.generateConcreteInterval(interval, tonic);
        concreteIntervalRepository.save( concreteInterval );
        return concreteInterval;
    }

    @Override
    public List<ConcreteInterval> generateAllConcreteIntervalsAndSave() {
        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        List<Interval> intervals = intervalService.getAll();
        List<Note> notes = noteService.getAll();

        for(Interval interval: intervals)
            for(Note note: notes)
                concreteIntervals.add(this.generateConcreteIntervalAndSave(interval, note));

        return concreteIntervals;
    }




    // Conversores

    @Override
    public ConcreteIntervalDTO concreteIntervalToConcreteIntervalDTO(ConcreteInterval concreteInterval) {
        ConcreteIntervalDTO concreteIntervalDTO = new ConcreteIntervalDTO();

        Interval interval = concreteInterval.getIntervalOfNotes();

        concreteIntervalDTO.setName( interval.getName() );
        concreteIntervalDTO.setSymbol( interval.getSymbol() );
        concreteIntervalDTO.setId( interval.getId() );
        concreteIntervalDTO.setSemitones( interval.getSemitones() );
        concreteIntervalDTO.setFirsNote( noteService.entityToDTO(concreteInterval.getFirstNote()) );
        concreteIntervalDTO.setLastNote( noteService.entityToDTO(concreteInterval.getLastNote()) );

        return concreteIntervalDTO;
    }

    @Override
    public List<ConcreteIntervalDTO> concreteIntervalsToConcreteIntervalDTOS(List<ConcreteInterval> concreteIntervals) {
        List<ConcreteIntervalDTO> concreteIntervalDTOS = new ArrayList<>();
        for(ConcreteInterval concreteInterval: concreteIntervals){
            concreteIntervalDTOS.add( this.concreteIntervalToConcreteIntervalDTO(concreteInterval) );
        }
        return concreteIntervalDTOS;
    }










    // ConcreteScale
    // Obtener
    @Override
    public Optional<ConcreteScale> getConcreteScale(Scale scale, Note tonic) {
        return concreteScaleRepository.getConcreteScale(scale.getId(), tonic.getId());
    }

    @Override
    public List<ConcreteScale> getConcreteScaleByScale(Scale scale) {
        List<ConcreteScale> concreteScales = concreteScaleRepository.getConcreteScalesByScale(scale.getId());
        return concreteScales == null ? new ArrayList<>() : concreteScales;
    }

    @Override
    public void saveConcreteScale(ConcreteScale concreteScale) {
        concreteScaleRepository.save(concreteScale);
    }




    // Utilidades
    @Override
    public Long getLastConcreteScaleId() {
        Long id = concreteScaleRepository.getMaxId();
        return id == null ? 0 : id;
    }



    // Generar
    @Override
    public Optional<ConcreteScale> generateConcreteScale(Scale scale, Note note, Long id, int position) {
        if( !scaleService.exist(scale) || !noteService.exist(note) ) return null;

        ConcreteScaleId concreteScaleId = new ConcreteScaleId();
        concreteScaleId.setId_note( note.getId() );
        concreteScaleId.setId_scale( scale.getId() );
        concreteScaleId.setId_concrete_scale( id );
        concreteScaleId.setPosition( position );

        ConcreteScale concreteScale = new ConcreteScale();
        concreteScale.setId(concreteScaleId);
        concreteScale.setNotesOfScale(note);
        concreteScale.setScaleOfNotes(scale);

        return Optional.of(concreteScale);
    }

    @Override
    public List<ConcreteScale> generateCompleteConcreteScales(Scale scale, Note tonic) {
        String codeString[] = scale.getCode().split("–");
        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        List<ConcreteScale> concreteScales = new ArrayList<>();

        int code = 0;
        for(String it:codeString){
            code += Integer.parseInt(it);
            Optional<Note> note = noteService.getById( this.getNoteWithSemitone(tonic.getId(), code) );
            Interval interval = intervalService.getIntervalWithSemitone(code).get();
            concreteIntervals.add( this.getConcreteInterval(interval, tonic, note.get()) );
        }

        int position = 1;
        Long id = this.getLastConcreteScaleId() + 1;
        concreteScales.add( this.generateConcreteScale( scale, tonic, id, position).get() );
        for(ConcreteInterval concreteInterval: concreteIntervals){
            position++;
            concreteScales.add( this.generateConcreteScale( scale, concreteInterval.getLastNote(), id, position ).get() );
        }

        return concreteScales;
    }

    @Override
    public List<ConcreteScale> generateAllConcreteScalesAndSave() {
        List<Scale> scales = scaleService.getAll();
        List<Note> notes = noteService.getAll();

        List<ConcreteScale> concreteScales = new ArrayList<>();

        for(Scale scale:scales){
            for(Note note: notes){
                List<ConcreteScale> itScales = this.generateCompleteConcreteScales( scale, note );
                concreteScales.addAll( itScales );
                concreteScaleRepository.saveAll( itScales );
            }
        }
        return concreteScales;
    }



    // Conversions
    @Override
    public ConcreteScaleDTO concreteScalesToConcreteScaleDTO(List<ConcreteScale> concreteScales) {
        Scale scale = concreteScales.get(0).getScaleOfNotes();

        ConcreteScaleDTO concreteScaleDTO = new ConcreteScaleDTO();
        concreteScaleDTO.setId( scale.getId() );
        concreteScaleDTO.setCode( scale.getCode() );
        concreteScaleDTO.setName( scale.getName() );
        concreteScaleDTO.setSymbol( scale.getSymbol() );

        HashMap<NoteDTO, Integer> notes = new HashMap<>();
        for(ConcreteScale concreteScale: concreteScales){
            NoteDTO  note = noteService.entityToDTO( concreteScale.getNotesOfScale() );
            notes.put( note, concreteScale.getId().getPosition() );
            if( concreteScale.getId().getPosition() == 1 ) concreteScaleDTO.setTonic( note );
        }
        concreteScaleDTO.setNotes( notes );

        return concreteScaleDTO;
    }
}
