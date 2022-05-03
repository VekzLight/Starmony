package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ConcreteScaleRepository;
import com.kadli.starmony.utilities.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("ConcreteScaleService")
public class ConcreteScaleServiceImp implements ConcreteScaleService{

    // Servicios
    @Autowired
    private NoteService noteService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private IntervalService intervalService;


    // Repositorios
    @Autowired
    private ConcreteScaleRepository concreteScaleRepository;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;





    // Obtener
    @Override
    public List<ConcreteScale> getAll() {
        return concreteScaleRepository.findAll();
    }

    @Override
    public List<ConcreteScale> getCompleteConcreteScaleById(Long id) {
        return concreteScaleRepository.getCompleteConcreteScale( id );
    }

    @Override
    public List<ConcreteScale> getCompleteConcreteScaleWithTonic(Long idScale, Long idTonic) {
        Long idConcreteScale = concreteScaleRepository.getIdConcreteScale(idScale,idTonic);
        if( idConcreteScale == null ) return new ArrayList<>();
        return this.getCompleteConcreteScaleById(idConcreteScale);
    }

    @Override
    public Optional<ConcreteScale> getConcreteScaleWithTonic(Long idscale, Long idTonic) {
        return concreteScaleRepository.getConcreteScale(idscale, idTonic);
    }


    /*@Override
    public List<ConcreteScale> getAllConcreteScalesByIdScale(Long id) {
        List<ConcreteScale> concreteScales = concreteScaleRepository.getConcreteScalesByScale(id);
        return concreteScales == null ? new ArrayList<>() : concreteScales;
    }*/

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

    @Override
    public Long getIdConcreteScale(Long idScale, Long idNote) {
        return concreteScaleRepository.getIdConcreteScale(idScale, idNote);
    }


    // Generar
    @Override
    public Optional<ConcreteScale> generateConcreteScale(Scale scale, Note note, Long id, int position) {
        if( !scaleService.exist(scale) || !noteService.exist(note) ) return null;

        ConcreteScaleId concreteScaleId = new ConcreteScaleId();
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
        String codeString[] = scale.getCode().split(Symbols.SYMBOL_SEPARATION_SCALE);
        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        List<ConcreteScale> concreteScales = new ArrayList<>();

        int code = 0;
        for(String it:codeString){
            code += Integer.parseInt(it);
            Optional<Note> note = noteService.getById( noteService.getNoteWithSemitone(tonic.getId(), code) );
            Interval interval = intervalService.getIntervalWithSemitone(code).get();
            concreteIntervals.add( concreteIntervalService.getConcreteIntervalWithTonic(interval.getId(), tonic.getId()).get() );
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
    public List<ConcreteScale> generateCompleteConcreteScalesAndSave(Scale scale, Note tonic) {
        List<ConcreteScale> concreteScales = this.generateCompleteConcreteScales(scale, tonic);
        concreteScaleRepository.saveAll(concreteScales);
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
        concreteScaleDTO.setIdConcrete( concreteScales.get(0).getId().getId_concrete_scale() );

        HashMap<Integer, NoteDTO> notes = new HashMap<>();
        for(ConcreteScale concreteScale: concreteScales){
            NoteDTO  note = noteService.entityToDTO( concreteScale.getNotesOfScale() );
            notes.put( concreteScale.getId().getPosition(), note );
            if( concreteScale.getId().getPosition() == 1 ) concreteScaleDTO.setTonic( note );
        }
        concreteScaleDTO.setNotes( notes );

        return concreteScaleDTO;
    }

}
