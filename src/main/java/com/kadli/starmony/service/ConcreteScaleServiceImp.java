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

    @Override
    public List<Long> getAllIds() {
        return concreteScaleRepository.getAllIds();
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
        if( !noteService.exist(note) ) return null;

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
        List<ConcreteScale> concreteScales = new ArrayList<>();

        Long idNote = tonic.getId();
        Long idConcreteScale = this.getLastConcreteScaleId() + 1L;
        Note currentNote = noteService.getById(idNote).get();
        int position = 1;

        concreteScales.add( this.generateConcreteScale( scale, currentNote, idConcreteScale, position).get() );
        for(String it:codeString){
            idNote += Long.parseLong(it);
            position++;
            if( idNote > 12L ) idNote -= 12L;
            currentNote = noteService.getById(idNote).get();
            concreteScales.add( this.generateConcreteScale( scale, currentNote, idConcreteScale, position).get() );
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
        concreteScaleDTO.setId_concrete_scale( concreteScales.get(0).getId().getId_concrete_scale() );

        HashMap<Integer, NoteDTO> notes = new HashMap<>();
        for(ConcreteScale concreteScale: concreteScales){
            NoteDTO  note = noteService.entityToDTO( concreteScale.getNotesOfScale() );
            notes.put( concreteScale.getId().getPosition(), note );
            if( concreteScale.getId().getPosition() == 1 ) concreteScaleDTO.setTonic( note );
        }
        concreteScaleDTO.setNotes( notes );

        return concreteScaleDTO;
    }

    @Override
    public List<ConcreteScaleDTO> getConcreteScalesWithTonicAndNotes(List<Long> idNotes, Long idTonic) {
        List<Long> idConcreteScales = concreteScaleRepository.getIdConcreteScalesWithTonic(idTonic);
        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();

        for(Long idConcreteScale: idConcreteScales){
            List<ConcreteScale> concreteScales = concreteScaleRepository.getCompleteConcreteScale(idConcreteScale);

            int count = 0;
            for(ConcreteScale concreteScale: concreteScales){
                if( idNotes.contains(concreteScale.getNotesOfScale().getId()) ) {
                    System.out.println( concreteScale.getNotesOfScale().getId() );
                    count++;
                }
            }

            if(count == idNotes.size() + 1){
                System.out.println("Escala encontrada");
                concreteScaleDTOS.add( this.concreteScalesToConcreteScaleDTO(concreteScales) );
            }
        }
        return concreteScaleDTOS;
    }

    @Override
    public List<Long> getIdConcreteScalesWithConcreteChords(List<Long> idConcreteChords) {
        return concreteScaleRepository.getIdConcreteScaleWithConcreteChords(idConcreteChords);
    }

}
