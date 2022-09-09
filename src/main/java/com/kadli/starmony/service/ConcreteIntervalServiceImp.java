package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteIntervalDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ConcreteIntervalRepository;
import com.kadli.starmony.repository.ConcreteScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ConcreteIntervalService")
public class ConcreteIntervalServiceImp implements ConcreteIntervalService{

    @Autowired
    private ConcreteIntervalRepository concreteIntervalRepository;

    @Autowired
    private ConcreteScaleRepository concreteScaleRepository;

    @Autowired
    private NoteService noteService;

    @Autowired
    private IntervalService intervalService;

    // Utilidad
    private Long getLastId(){
        Long id = concreteIntervalRepository.getMaxId();
        return id == null ? 0 : id;
    }



    // Obtener
    @Override
    public List<ConcreteInterval> getAllConcreteIntervals() {
        return concreteIntervalRepository.findAll();
    }

    @Override
    public Optional<ConcreteInterval> getConcreteIntervalById(Long id) {
        return concreteIntervalRepository.getConcreteIntervalById(id);
    }


    @Override
    public Optional<ConcreteInterval> getConcreteIntervalWithTonic(Long idInterval, Long idTonic) {
        return concreteIntervalRepository.getConcreteIntervalWithTonic(idInterval,idTonic );
    }





    // Generadores
    @Override
    public ConcreteInterval generateConcreteInterval(Interval interval, Note tonic) {
        int semitones = interval.getSemitones();
        Optional<Note> note = noteService.getById( noteService.getNoteWithSemitone(tonic.getId(), semitones) );

        if( !note.isPresent() ) return null;

        ConcreteInterval concreteInterval = new ConcreteInterval();
        concreteInterval.setId_concrete_interval( this.getLastId() + 1);
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
        concreteIntervalDTO.setId_concrete_interval( concreteInterval.getId_concrete_interval() );
        concreteIntervalDTO.setFirstNote( noteService.entityToDTO(concreteInterval.getFirstNote()) );
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

    @Override
    public List<ConcreteIntervalDTO> getConcreteIntervalsWithNotesAndTonic(Long tonicId, List<Long> noteDTOS) {
        List<ConcreteIntervalDTO> concreteIntervalDTOS = new ArrayList<>();
        for(Long idNote: noteDTOS){
            List<ConcreteInterval> concreteIntervals = concreteIntervalRepository.getConcreteIntervalWithTonicAndNote(tonicId, idNote);
            concreteIntervalDTOS.addAll( this.concreteIntervalsToConcreteIntervalDTOS(concreteIntervals) );
        }
        return concreteIntervalDTOS;
    }

    @Override
    public List<Long> getIdConcreteIntervalsOfConcreteScale(Long concreteScaleId) {
        Scale scale = concreteScaleRepository.getScaleFromConcreteScale(concreteScaleId);
        Long idTonic = concreteScaleRepository.getIdTonicOfConcreteScaleId(concreteScaleId);

        List<Interval> intervals = intervalService.getIntervalsOfScaleByTonic(scale);
        List<Long> intervalsIds = new ArrayList<>();
        for(Interval interval: intervals)
            intervalsIds.add( concreteIntervalRepository.getIdConcreteIntervalWithIntervalAndTonic(interval.getId(), idTonic) );


        return intervalsIds;
    }


}
