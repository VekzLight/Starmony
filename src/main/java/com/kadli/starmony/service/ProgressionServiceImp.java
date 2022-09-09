package com.kadli.starmony.service;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ProgressionGradeRepository;
import com.kadli.starmony.repository.ProgressionRepository;
import com.kadli.starmony.repository.TagProgressionRepository;
import com.kadli.starmony.utilities.Symbols;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ProgressionService")
public class ProgressionServiceImp implements ProgressionService{

    @Autowired
    private TagProgressionRepository tagProgressionRepository;

    @Autowired
    private ProgressionRepository progressionRepository;

    @Autowired
    private ProgressionGradeRepository progressionGradeRepository;


    @Autowired
    private ChordService chordService;

    @Autowired
    private ScaleService scaleService;


    @Autowired
    private ModelMapper modelMapper;


    // Utilidades
    public List<Progression> getAllManually(Progression example){
        Long id = example.getId();
        String name = example.getName();
        String symbol = example.getSymbol();
        String code = example.getCode();

        List<Progression> progressions = new ArrayList<>();
        if(id != null) this.getById(id).ifPresent(it -> { progressions.add(it); });
        if(name != null) this.getByAttribute("name", name).ifPresent(it -> { progressions.add(it); });
        if(symbol != null) this.getByAttribute("symbol", symbol).ifPresent(it -> { progressions.add(it); });
        if(code != null) this.getByAttribute("code", code).ifPresent(it -> { progressions.add(it); });

        return progressions;
    }









    // Obtener
    @Override
    public List<Progression> getAll() {
        return progressionRepository.findAll();
    }

    @Override
    public List<Progression> getAll(Progression example) {
        List<Progression> progressions = progressionRepository.findAll( Example.of(example) );
        if( progressions.isEmpty() ) progressions = this.getAllManually(example);
        return progressions;}

    @Override
    public Optional<Progression> get(Progression example) {
        Optional<Progression> progression = progressionRepository.findOne(Example.of(example));
        if( !progression.isPresent() ){
            List<Progression> progressions = this.getAllManually(example);
            if( !progressions.isEmpty() ) progression = Optional.of(progressions.get(0));
        }
        return progression;
    }

    @Override
    public Optional<Progression> getById(Long id) {
        return progressionRepository.findById(id);
    }

    @Override
    public Optional<Progression> getByAttribute(String attribute, String value) {
        return progressionRepository.findByAttribute(attribute, value);
    }






    // Guardar
    @Override
    public void save(Progression entity) {
        progressionRepository.save(entity);
    }










    // Eliminar
    @Override
    public void delete(Progression entity) {
        progressionRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        progressionRepository.deleteById(id);
    }

    @Override
    public void deleteByAttribute(String attribute, String value) {

    }






    // Comprobar
    @Override
    public boolean exist(Progression entity) {
        boolean existIt = progressionRepository.exists(Example.of(entity));
        if(!existIt) existIt = !this.getAllManually(entity).isEmpty();
        return existIt;
    }

    @Override
    public boolean existById(Long id) {
        return progressionRepository.existsById(id);
    }

    @Override
    public boolean existByAttribute(String attribute, String value) {
        return this.getByAttribute(attribute,value).isPresent();
    }








    // Actualizar
    @Override
    public void updateAttributeById(Long id, String attribute, String value) {
        this.getById(id).ifPresent( progression -> {
            switch (attribute){
                case "name": progression.setName(value); break;
                case "symbol": progression.setSymbol(value); break;
                case "semitones": progression.setCode(value); break;
            }

            this.save(progression);
        });
    }







    // Conversiones
    @Override
    public ProgressionDTO entityToDTO(Progression entity) {
        return modelMapper.map(entity, ProgressionDTO.class);
    }

    @Override
    public Progression dtotoEntity(ProgressionDTO dto) {
        return modelMapper.map(dto, Progression.class);
    }





    // Generators
    @Override
    public List<ProgressionGrade> generateProgressionGradeSimple(Progression progression, List<ScaleGrade> scaleGrades) {
        String codeGrades[] = progression.getSymbol().split(Symbols.SYMBOL_SEPARATION_PROGRESSION );

        List<ProgressionGrade> progressionGrades = new ArrayList<>();
        HashMap<String, ScaleGrade> grades = new HashMap<>();

        for(ScaleGrade scaleGrade: scaleGrades){
            grades.put(scaleGrade.getId().getGrade(), scaleGrade);
        }


        Long idProgresion = this.getLastId() + 1;
        int position = 1;

        for(String code:codeGrades){
            String symbol = code;
            Pattern searchVariables = Pattern.compile("[VIX]$");
            Matcher symbolMatcher = searchVariables.matcher(symbol);

            if( !symbolMatcher.find() ){
                Pattern replace = Pattern.compile("[^VIX].*");
                Matcher matcher = replace.matcher(code);
                matcher.find();

                symbol = matcher.replaceAll("");
            }


            ScaleGrade scaleGrade = grades.get(symbol);

            // No se puede generar la progression en esta escala
            if(scaleGrade == null) return new ArrayList<>();

            ProgressionGradeId progressionGradeId = new ProgressionGradeId();
            progressionGradeId.setId_progression_grade( idProgresion );
            progressionGradeId.setPosition( position );

            ProgressionGrade progressionGrade = new ProgressionGrade();
            progressionGrade.setScaleGradeOfProgression(scaleGrade);
            progressionGrade.setProgressionOfProgressionGrade(progression);
            progressionGrade.setId(progressionGradeId);

            progressionGrades.add(progressionGrade);

            position++;
        }

        //System.out.println("Progression = " + idProgresion + " Code = " + progression.getSymbol());
        return progressionGrades;
    }

    @Override
    public List<ProgressionGrade> generateProgressionGradeForce(Progression progression, List<ScaleGrade> scaleGrades) {
        String codeGrades[] = progression.getSymbol().split(Symbols.SYMBOL_SEPARATION_PROGRESSION );

        List<ProgressionGrade> progressionGrades = new ArrayList<>();
        HashMap<String, ScaleGrade> grades = new HashMap<String, ScaleGrade>();

        for(ScaleGrade scaleGrade: scaleGrades)
            grades.put(scaleGrade.getId().getGrade(), scaleGrade);


        Long idProgresion = this.getLastId() + 1;
        int position = 1;

        for(String code:codeGrades){
            String symbol = code;
            AtomicReference<ScaleGrade> scaleGrade = new AtomicReference<>();

            Pattern searchVariables = Pattern.compile("[VIX]$");
            Matcher symbolMatcher = searchVariables.matcher(symbol);

            if( !symbolMatcher.find() ){
                Pattern replace = Pattern.compile("[VIX]+");
                Matcher matcher = replace.matcher(code);
                matcher.find();

                symbol = matcher.replaceAll("");
                System.out.println(symbol);
                scaleGrade.set( grades.get(symbol) );
            } else {
                System.out.println("-");
                scaleGrade.set( grades.get("-") );
            }

            if(scaleGrade.get() == null){
                Pattern replace = Pattern.compile("[^VIX].*");
                Matcher matcher = replace.matcher(code);
                matcher.find();

                symbol = matcher.replaceAll("");
                System.out.println(symbol);

                scaleGrade.set( grades.get(symbol) );
            }

            if( scaleGrade.get() == null ) return new ArrayList<>();

            ProgressionGradeId progressionGradeId = new ProgressionGradeId();
            progressionGradeId.setId_progression_grade( idProgresion );
            progressionGradeId.setPosition( position );

            ProgressionGrade progressionGrade = new ProgressionGrade();
            progressionGrade.setScaleGradeOfProgression(scaleGrade.get());
            progressionGrade.setProgressionOfProgressionGrade(progression);
            progressionGrade.setId(progressionGradeId);

            progressionGrades.add(progressionGrade);

            position++;
        }

        return progressionGrades;
    }

    @Override
    public List<ProgressionGrade> generateAndSaveProgressionGradeSimple(Progression progression,List<ScaleGrade> scaleGrades) {
        List<ProgressionGrade> progressionGrades = this.generateProgressionGradeSimple(progression,scaleGrades);
        if( !progressionGrades.isEmpty() )
            for(ProgressionGrade progressionGrade: progressionGrades){
                progressionGradeRepository.save(progressionGrade);
            }

        return progressionGrades;
    }

    @Override
    public List<ProgressionGrade> generateAndSaveAllProgresionGradeSimple() {
        List<Progression> progressions = progressionRepository.findAll();
        List<Scale> scales = scaleService.getAll();
        List<ProgressionGrade> progressionGrades = new ArrayList<>();
        for(Progression progression: progressions){
            for(Scale scale: scales){
                List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale);
                progressionGrades.addAll(this.generateAndSaveProgressionGradeSimple(progression,scaleGrades));
            }
        }
        return progressionGrades;
    }

    @Override
    public Optional<ProgressionGradeDTO> progressionGradeToProgressionGradeDTO(List<ProgressionGrade> progressionGrades) {
        HashMap<Integer, ChordDTO> grades = new HashMap<>();
        HashMap<Integer, String> parseGrade = new HashMap<>();

        ProgressionGradeDTO progressionGradeDTO = new ProgressionGradeDTO();

        Scale scale = progressionGrades.get(0).getScaleGradeOfProgression().getScaleOfChord();
        ScaleDTO scaleDTO = scaleService.entityToDTO(scale);

        String[] gradeString = progressionGrades.get(0).getProgressionOfProgressionGrade().getSymbol().split( Symbols.SYMBOL_SEPARATION_PROGRESSION );
        int positionIt = 1;
        for(String it: gradeString){
            parseGrade.put(positionIt, it);
            positionIt++;
        }

        for(ProgressionGrade progressionGrade: progressionGrades){
            Chord grade = progressionGrade.getScaleGradeOfProgression().getChordOfScale();
            ChordDTO chordDTO = chordService.entityToDTO(grade);

            grades.put( progressionGrade.getId().getPosition(),  chordDTO);
        }

        Progression progression = progressionGrades.get(0).getProgressionOfProgressionGrade();

        progressionGradeDTO.setId(progression.getId() );
        progressionGradeDTO.setName(progression.getName());
        progressionGradeDTO.setSymbol(progression.getSymbol());
        progressionGradeDTO.setCode( progression.getCode() );
        progressionGradeDTO.setGrades(grades);
        progressionGradeDTO.setScale(scaleDTO);

        progressionGradeDTO.setId_progression_grade( progressionGrades.get(0).getId().getId_progression_grade() );

        return Optional.of(progressionGradeDTO);
    }

    @Override
    public List<ProgressionGrade> getCompleteProgressionGradeByScaleGrade(Long idProgression, Long idScaleGrade) {
        return progressionGradeRepository.getProgressionGradesByScaleGrade(idProgression, idScaleGrade);
    }

    @Override
    public List<ProgressionGrade> getCompleteProgressionGradeById(Long idProgressionGrade) {
        return progressionGradeRepository.getProgressionGradesById(idProgressionGrade);
    }


    @Override
    public Long getLastId(){
        Long id = progressionGradeRepository.getLastId();
        return id == null ? 0 : id;
    }

    @Override
    public Long getIdProgressionGradeByScaleGrade(Long idProgression, Long idScaleGrade) {
        return progressionGradeRepository.getIdProgressionGradeByProgressionAndSG(idProgression, idScaleGrade);
    }

    @Override
    public List<Long> getIdProgressionsByTag(Long idTag){
        return tagProgressionRepository.getIdProgressionsOfIdTag(idTag);
    }

    @Override
    public List<Progression> getAllWithLenth(int size) {
        return progressionRepository.getAllWithLenth(size);
    }
}
