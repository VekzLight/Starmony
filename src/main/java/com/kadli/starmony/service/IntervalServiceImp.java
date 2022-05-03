package com.kadli.starmony.service;

import com.kadli.starmony.dto.IntervalDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("IntervalService")
public class IntervalServiceImp implements IntervalService {

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private ChordIntervalRepository chordIntervalRepository;


    @Autowired
    private ChordService chordService;


    @Autowired
    private ModelMapper modelMapper;



    public List<Interval> getAllManually(Interval interval){
        Long id = interval.getId();
        String name = interval.getName();
        String symbol = interval.getSymbol();
        int semitones = interval.getSemitones();

        List<Interval> intervals = new ArrayList<>();
        if(id != null) this.getById(id).ifPresent(it -> { intervals.add(it); });
        if(name != null) this.getByAttribute("name", name).ifPresent(it -> { intervals.add(it); });
        if(symbol != null) this.getByAttribute("symbol", symbol).ifPresent(it -> { intervals.add(it); });
        if(semitones > 0) this.getIntervalWithSemitone(semitones).ifPresent(it -> { intervals.add(it); });

        return intervals;
    }


    // Intervalos
    // Obtener
    @Override
    public List<Interval> getAll() {
        return intervalRepository.findAll();
    }

    @Override
    public List<Interval> getAll(Interval example) {
        List<Interval> intervals = intervalRepository.findAll( Example.of(example) );
        if( intervals.isEmpty() ) intervals = this.getAllManually(example);
        return intervals;
    }

    @Override
    public Optional<Interval> get(Interval example) {
        Optional<Interval> interval = intervalRepository.findOne(Example.of(example));
        if( !interval.isPresent() ){
            List<Interval> intervals = this.getAllManually(example);
            if( !intervals.isEmpty() )
                interval = Optional.of(intervals.get(0));
        }
        return interval;
    }

    @Override
    public Optional<Interval> getById(Long id) {
        return intervalRepository.findById(id);
    }

    @Override
    public Optional<Interval> getByAttribute(String attribute, String value) {
        return intervalRepository.findByAttribute(attribute, value);
    }




    // Guardar
    @Override
    public void save(Interval entity) {
        intervalRepository.save(entity);
    }





    // Eliminar
    @Override
    public void delete(Interval entity) {
        intervalRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        intervalRepository.deleteById(id);
    }

    @Override
    public void deleteByAttribute(String attribute, String value) {
        this.getByAttribute(attribute, value).ifPresent((interval -> {
            this.delete(interval);
        }));
    }




    // Comprobaciones
    @Override
    public boolean exist(Interval entity) {
        boolean existIt = intervalRepository.exists(Example.of(entity));
        if(!existIt) existIt = !this.getAllManually(entity).isEmpty();
        return existIt;
    }

    @Override
    public boolean existById(Long id) {
        return intervalRepository.existsById(id);
    }

    @Override
    public boolean existByAttribute(String attribute, String value) {
        return this.getByAttribute(attribute, value).isPresent();
    }




    // Actualizar
    @Override
    public void updateAttributeById(Long id, String attribute, String value) {
        this.getById(id).ifPresent(interval -> {
            switch (attribute){
                case "name": interval.setName(value); break;
                case "symbol": interval.setSymbol(value); break;
                case "semitones": interval.setSemitones(Integer.parseInt(value)); break;
            }

            this.save(interval);
        });
    }




    //
    // Intervalos de Escalas
    //

    @Override
    public List<Interval> getIntervalsOfScaleByTonic(Scale scale) {
        return intervalRepository.getIntervalsOfScaleCodeByTonic(scale.getCode());
    }

    @Override
    public List<Interval> getIntervalsOfScaleByAll(Scale scale) {
        return intervalRepository.getIntervalsOfScaleCodeByAll(scale.getCode());
    }





    // Intervalos de Acordes

    // Obtener
    @Override
    public List<Interval> getIntervalsOfChord(Chord chord) {
        return intervalRepository.getIntervalsOfChord( chord.getId() );
    }





    // Utilidades
    private List<ChordInterval> toChordIntervals(Chord chord, List<Interval> intervals){
        List<ChordInterval> chordIntervals = new ArrayList<>();

        Long position = 1L;
        Long idChordInterval = this.getLastId() + 1;
        for(Interval interval: intervals){
            ChordIntervalId id = new ChordIntervalId();
            id.setPosition_interval(position);
            id.setId_chord_interval(idChordInterval);

            ChordInterval it = new ChordInterval();
            it.setChordOfInterval(chord);
            it.setIntervalOfChord(interval);
            it.setId(id);

            chordIntervals.add(it);
            position++;
        }

        return chordIntervals;
    }

    @Override
    public List<Interval> generateIntervalsOfChord(Chord chord) {
        String codeString[] = chord.getCode().split("-");
        List<Interval> intervals = new ArrayList<>();
        for(String symbol:codeString){
            String subCode = symbol;
            if(!symbol.contains("2b")){
                if (symbol.contains("b")) subCode = (Integer.parseInt(subCode.split("b")[0]) - 1)+"#/" + symbol;
                if (symbol.contains("#")) subCode = symbol + "/" + (Integer.parseInt(subCode.split("#")[0]) + 1) + "b";
            }

            intervalRepository.findByAttribute("symbol", subCode).ifPresent( (interval) -> {
                intervals.add(interval);
            });
        }
        return intervals;
    }







    // Generadores
    @Override
    public List<ChordInterval> generateIntervalsOfChordAndSave(Chord chord) {
        List<Interval> intervals = this.generateIntervalsOfChord(chord);
        List<ChordInterval> chordIntervals = this.toChordIntervals(chord, intervals);
        this.chordIntervalRepository.saveAll(chordIntervals);
        return chordIntervals;
    }

    @Override
    public List<ChordInterval> generateAllIntervalsOfChordsAndSave() {
        List<Chord> chords = chordService.getAll();
        List<ChordInterval> chordIntervals = new ArrayList<>();
        for(Chord it: chords){
            chordIntervals.addAll(this.generateIntervalsOfChordAndSave(it));
        }
        return chordIntervals;
    }




    // Obtener
    @Override
    public Optional<Interval> getIntervalWithSemitone(int semitone) {
        return intervalRepository.getIntervalWithSemitones(semitone);
    }

    @Override
    public List<Interval> getIntervalsOfNotes(List<Note> notes) {
        List<Long> notesId = new ArrayList<>();
        for(Note note: notes)
            notesId.add(note.getId());
        return intervalRepository.getIntervalsOfNotes(notesId);
    }

    @Override
    public Long getLastId() {
        Long id = chordIntervalRepository.getLastId();
        return id == null ? 0 : id;
    }


    // Conversiones
    @Override
    public IntervalDTO entityToDTO(Interval entity) {
        return modelMapper.map(entity, IntervalDTO.class);
    }


    @Override
    public Interval dtotoEntity(IntervalDTO dto) {
        return modelMapper.map(dto, Interval.class);
    }

}
