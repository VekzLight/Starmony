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



    // Intervalos
    // Obtener
    @Override
    public List<Interval> getAll() {
        return intervalRepository.findAll();
    }

    @Override
    public Optional<Interval> getById(Long id) {
        return intervalRepository.findById(id);
    }

    @Override
    public Optional<Interval> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getBySymbol(String symbol) {
        return Optional.empty();
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
    public void deleteByName(String name) {
        this.getByName(name).ifPresent((interval -> {
            this.delete(interval);
        }));
    }

    @Override
    public void deleteBySymbol(String symbol) {
        this.getBySymbol(symbol).ifPresent((interval -> {
            this.delete(interval);
        }));
    }

    @Override
    public void deleteByCode(String code) {
        this.getByCode(code).ifPresent((interval -> {
            this.delete(interval);
        }));
    }



    // Comprobaciones
    @Override
    public boolean exist(Interval entity) {
        return intervalRepository.exists(Example.of(entity));
    }

    @Override
    public boolean existById(Long id) {
        return intervalRepository.existsById(id);
    }

    @Override
    public boolean existByName(String name) {
        return intervalRepository.exists(Example.of(Interval.builder().name(name).build()));
    }

    @Override
    public boolean existBySymbol(String symbol) {
        return intervalRepository.exists(Example.of(Interval.builder().symbol(symbol).build()));
    }

    @Override
    public boolean existByCode(String code) {
        return intervalRepository.exists(Example.of(Interval.builder().semitones(Integer.parseInt(code)).build()));

    }



    // Actualizar
    @Override
    public void updateNameById(Long id, String name) {
        intervalRepository.findById(id).ifPresent(interval -> {
            interval.setName(name);
            intervalRepository.save(interval);
        });
    }

    @Override
    public void updateSymbolById(Long id, String symbol) {
        intervalRepository.findById(id).ifPresent(interval -> {
            interval.setSymbol(symbol);
            intervalRepository.save(interval);
        });
    }

    @Override
    public void updateCodeById(Long id, String code) {
        intervalRepository.findById(id).ifPresent(interval -> {
            interval.setSemitones(Integer.parseInt(code));
            intervalRepository.save(interval);
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


    // Generadores
    private List<ChordIntervals> toChordIntervals(Chord chord, List<Interval> intervals){
        List<ChordIntervals> chordIntervals = new ArrayList<>();

        for(Interval interval: intervals){
            ChordIntervalsId itId = new ChordIntervalsId();
            itId.setId_interval(interval.getId());
            itId.setId_chord(chord.getId());

            ChordIntervals it = new ChordIntervals();
            it.setChordOfInterval(chord);
            it.setIntervalOfChord(interval);
            it.setId(itId);

            chordIntervals.add(it);
        }

        return chordIntervals;
    }

    @Override
    public List<Interval> generateIntervalsOfChord(Chord chord) {
        String codeString[] = chord.getCode().split("-");
        List<Interval> intervals = new ArrayList<>();
        for(String symbol:codeString){
            System.out.println(symbol);

            intervalRepository.findByAttribute("symbol", symbol).ifPresent( interval -> {
                intervals.add(interval);
            });
        }
        return intervals;
    }

    // Generadores
    @Override
    public void generateIntervalsOfChordAndSave(Chord chord) {
        List<Interval> intervals = this.generateIntervalsOfChord(chord);
        List<ChordIntervals> chordIntervals = this.toChordIntervals(chord, intervals);
        this.chordIntervalRepository.saveAll(chordIntervals);
    }

    @Override
    public void generateAllIntervalsOfChordsAndSave() {
        List<Chord> chords = chordService.getAll();
        for(Chord it: chords){
            this.generateIntervalsOfChordAndSave(it);
        }
    }




    // Obtener
    @Override
    public Optional<Interval> getIntervalWithSemitone(int semitone) {
        return intervalRepository.getIntervalWithSemitones(semitone);
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
