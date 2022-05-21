package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ScaleGradesDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.*;
import com.kadli.starmony.utilities.Symbols;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ChordService")
public class ChordServiceImp implements ChordService {

    // Repositorios de la clase
    @Autowired
    private ChordRepository chordRepository;

    @Autowired
    private ChordIntervalRepository chordIntervalRepository;

    @Autowired
    private ScaleGradeRepository scaleGradeRepository;




    // Servicios Externos
    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;



    // Herramientas
    @Autowired
    private ModelMapper modelMapper;


    // Utilidades
    public List<Chord> getAllManually(Chord example){
        Long id = example.getId();
        String name = example.getName();
        String symbol = example.getSymbol();
        String code = example.getCode();

        List<Chord> chords = new ArrayList<>();
        if(id != null) this.getById(id).ifPresent(it -> { chords.add(it); });
        if(name != null) this.getByAttribute("name", name).ifPresent(it -> { chords.add(it); });
        if(symbol != null) this.getByAttribute("symbol", symbol).ifPresent(it -> { chords.add(it); });
        if(code != null) this.getByAttribute("code", code).ifPresent(it -> { chords.add(it); });

        return chords;
    }




    // Obtener
    @Override
    public List<Chord> getAll() {
        return chordRepository.findAll();
    }

    @Override
    public List<Chord> getAll(Chord example) {
        List<Chord> chords = chordRepository.findAll( Example.of(example) );
        if( chords.isEmpty() ) chords = this.getAllManually(example);
        return chords;
    }

    @Override
    public Optional<Chord> get(Chord example) {
        Optional<Chord> chord = chordRepository.findOne(Example.of(example));
        if( !chord.isPresent() ){
            List<Chord> chords = this.getAllManually(example);
            if( !chords.isEmpty() )
                chord = Optional.of(chords.get(0));
        }
        return chord;
    }

    @Override
    public Optional<Chord> getById(Long id) {
        return chordRepository.findById(id);
    }

    @Override
    public Optional<Chord> getByAttribute(String attribute, String value) { return chordRepository.findByAttribute(attribute, value); }





    // Guardar
    @Override
    public void save(Chord chord) {
        chordRepository.save(chord);
    }





    // Eliminar
    @Override
    public void delete(Chord chord) {
        chordRepository.delete(chord);
    }

    @Override
    public void deleteById(Long id) {
        chordRepository.deleteById(id);
    }

    @Override
    public void deleteByAttribute(String attribute, String value) {
        this.getByAttribute(attribute, value).ifPresent(chord -> {
            chordRepository.delete(chord);
        });
    }






    // Comprobacion

    @Override
    public boolean exist(Chord entity) {
        boolean existIt = chordRepository.exists(Example.of(entity));
        if(!existIt) existIt = !this.getAllManually(entity).isEmpty();
        return existIt;
    }

    @Override
    public boolean existById(Long id) {
        return chordRepository.existsById(id);
    }

    @Override
    public boolean existByAttribute(String attribute, String value) {
        return this.getByAttribute(attribute, value).isPresent();
    }






    // Actializar
    @Override
    public void updateAttributeById(Long id, String attribute, String value) {
        this.getById(id).ifPresent(chord -> {
            switch (attribute){
                case "name": chord.setName(value); break;
                case "symbol": chord.setSymbol(value); break;
                case "code": chord.setCode(value); break;
            }

            this.save(chord);
        });
    }






    // Conversiones
    @Override
    public ChordDTO entityToDTO(Chord entity) {
        return modelMapper.map(entity, ChordDTO.class);
    }

    @Override
    public Chord dtotoEntity(ChordDTO dto) {
        return modelMapper.map(dto, Chord.class);
    }



    // Grados de Escala

    private Long getMaxId(){
        Long id = scaleGradeRepository.getMaxId();
        return id == null ? 0 : id;
    }

    @Override
    public Long getIdScaleGrade(Scale scale){
        return scaleGradeRepository.getIdScaleGrade(scale.getId());
    }

    @Override
    public List<ScaleGrade> generateGradesOfScale(Scale scale) {
        String codeString[] = scale.getCode().split(Symbols.SYMBOL_SEPARATION_SCALE);
        List<ScaleGrade> scaleGrades = new ArrayList<>();

        Long idScaleGrade = this.getMaxId() + 1;
        for(int i = 0; i < codeString.length; i++){
            List<Interval> intervals = new ArrayList<>();
            int semitone= 0;
            int pointer = i - 1;

            for(int k = 0; k < 2; k++){
                for(int j  = 0; j < 2; j++){
                    pointer++;
                    if( pointer >= codeString.length ) pointer = 0;
                    System.out.println(codeString[pointer]);
                    semitone += Integer.parseInt( codeString[pointer] );
                    System.out.println(semitone);
                }

                intervalService.getIntervalWithSemitone(semitone).ifPresent(interval -> {
                    intervals.add( interval );
                });
            }

            int finalI = i + 1;
            for(Interval interval : intervals)
                System.out.println("interval =" + interval.getSymbol() + ":"+ interval.getId());

            chordRepository.getChordWithIntervals(intervals).ifPresentOrElse(chord -> {
                ScaleGradeId scaleGradeId = new ScaleGradeId();
                scaleGradeId.setGrade( Symbols.POS_TO_GRADE(finalI) );
                scaleGradeId.setId_scale_grade( idScaleGrade );

                ScaleGrade scaleGrade = new ScaleGrade();
                scaleGrade.setChordOfScale( chord );
                scaleGrade.setScaleOfChord( scale );
                scaleGrade.setId( scaleGradeId );

                scaleGrades.add(scaleGrade);
            }, () -> {

            });
        }

        return scaleGrades;
    }

    @Override
    public List<ScaleGrade> generateGradesOfScaleAndSave(Scale scale) {
        List<ScaleGrade> scaleGrades = this.generateGradesOfScale( scale );
        scaleGradeRepository.saveAll(scaleGrades);
        return scaleGrades;
    }

    @Override
    public void generateAllGradesOfScaleAndSave() {
        List<Scale> scales = scaleService.getAll();
        for(Scale scale: scales)
            this.generateGradesOfScaleAndSave( scale );
    }

    @Override
    public ScaleGradesDTO scaleGradesToScaleGradeDTO(List<ScaleGrade> scaleGrades) {
        ScaleGradesDTO scaleGradesDTO = new ScaleGradesDTO();

        if(scaleGrades.isEmpty()) return null;

        Scale scale = scaleGrades.get(0).getScaleOfChord();
        HashMap<Integer, ChordDTO> grades = new HashMap<>();

        for(ScaleGrade scaleGrade:scaleGrades ){
            Chord chord = scaleGrade.getChordOfScale();
            ChordDTO chordDTO = this.entityToDTO( chord );

            grades.put( Symbols.GRADE_TO_POS(scaleGrade.getId().getGrade()), chordDTO );
        }

        scaleGradesDTO.setGrades(grades);
        scaleGradesDTO.setId( scale.getId() );
        scaleGradesDTO.setIdScaleGrade( scaleGrades.get(0).getId().getId_scale_grade() );
        scaleGradesDTO.setCode(scale.getCode());
        scaleGradesDTO.setName(scale.getName());
        scaleGradesDTO.setSymbol(scale.getSymbol());

        return scaleGradesDTO;
    }

    @Override
    public List<Chord> getChordsWithIntervals(List<Interval> intervals) {
        List<Long> idChordIntervals = chordIntervalRepository.getIdChordIntervalsWithIntervals(intervals.stream().map(interval -> interval.getId()).collect(Collectors.toList()));
        List<Chord> chords = new ArrayList<>();
        for(Long idChordInterval: idChordIntervals){
            List<ChordInterval> chordIntervals = chordIntervalRepository.getChordIntervalsById(idChordInterval);

            int count = 0;
            for(ChordInterval chordInterval: chordIntervals){
                if( intervals.contains( chordInterval.getIntervalOfChord() ) ) count++;
            }

            if(count == intervals.size()){
                chords.add(chordIntervals.get(0).getChordOfInterval());
            }
        }
        return chords;
    }

    @Override
    public List<ScaleGrade> getGradesOfScale(Scale scale) {
        return scaleGradeRepository.getGradesOfScale(scale);
    }

    @Override
    public Optional<ScaleGradesDTO> scaleGradeToScaleGradeDTO(ScaleGrade grade) {
        HashMap<Integer, ChordDTO> grades = new HashMap<>();
        Chord chord = grade.getChordOfScale();
        grades.put( Symbols.GRADE_TO_POS(grade.getId().getGrade()), this.entityToDTO(chord));

        ScaleGradesDTO scaleGradesDTO = new ScaleGradesDTO();
        scaleGradesDTO.setIdScaleGrade( grade.getId().getId_scale_grade() );
        scaleGradesDTO.setGrades(grades);

        Scale scale = grade.getScaleOfChord();
        scaleGradesDTO.setId( scale.getId() );
        scaleGradesDTO.setCode(scale.getCode());
        scaleGradesDTO.setName(scale.getName());
        scaleGradesDTO.setSymbol(scale.getSymbol());
        return Optional.of( scaleGradesDTO );
    }

}
