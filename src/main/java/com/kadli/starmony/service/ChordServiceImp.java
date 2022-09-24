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

import java.util.*;
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

    private ScaleGrade chordToScaleGrade(Scale scale, Chord chord, int posGrade, Long idScaleGrade){
        ScaleGradeId scaleGradeId = new ScaleGradeId();
        scaleGradeId.setGrade( Symbols.POS_TO_GRADE(posGrade) );
        scaleGradeId.setId_scale_grade( idScaleGrade );

        ScaleGrade scaleGrade = new ScaleGrade();
        scaleGrade.setChordOfScale( chord );
        scaleGrade.setScaleOfChord( scale );
        scaleGrade.setId( scaleGradeId );
        scaleGrade.setExist(true);

        return scaleGrade;
    }

    /**
     *
     * @param scale
     * @return Map< numero notas, acordes >
     */
    @Override
    public HashMap<Long, List<ScaleGrade>> generateGradesOfScale(Scale scale) {
        HashMap<Long, List<ScaleGrade>>  scaleGrades = new HashMap<>();

        List<ScaleGrade> scaleGradesTriada = new ArrayList<>();
        List<ScaleGrade> scaleGradesSeptima = new ArrayList<>();
        List<ScaleGrade> scaleGradesNovena = new ArrayList<>();

        Long idScaleGrade = this.getMaxId() + 1;
        int[] codeStr = Arrays.stream(scale.getCode().trim().split(Symbols.SYMBOL_SEPARATION_SCALE)).mapToInt(Integer::parseInt).toArray();

        int posGrade = 1;
        for(int i = 0; i < codeStr.length; i++){
            int index = i;
            int semitones = codeStr[index];
            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];
            Interval interval_base = intervalService.getIntervalWithSemitone(semitones).get();

            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];

            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];
            Interval interval_triada = intervalService.getIntervalWithSemitone(semitones).get();

            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];

            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];
            Interval interval_septima = intervalService.getIntervalWithSemitone(semitones).get();

            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];

            index = index == codeStr.length - 1 ? 0 : index + 1;
            semitones += codeStr[index];
            Interval interval_novena = intervalService.getIntervalWithSemitone(semitones).get();

            // C E G - triada
            // C E G B - septima
            // C E G B D - novena
            List<Interval> intervalBuffer = new ArrayList<>();
            intervalBuffer.add(interval_base);
            intervalBuffer.add(interval_triada);

            List<Interval> triada = new ArrayList<>(intervalBuffer);

            intervalBuffer.add(interval_septima);
            List<Interval> septima = new ArrayList<>(intervalBuffer);

            intervalBuffer.add(interval_novena);
            List<Interval> novena = new ArrayList<>(intervalBuffer);

            List<Long> idTriada     = triada.stream().map( interval-> interval.getId() ).collect(Collectors.toList());
            List<Long> idSeptima    = septima.stream().map( interval-> interval.getId() ).collect(Collectors.toList());
            List<Long> idNovena     = novena.stream().map( interval-> interval.getId() ).collect(Collectors.toList());

            List<Long> chordsTriada = chordIntervalRepository.getChordIntervalsIdsWhereLength( 2L);
            List<Long> chordsSeptima = chordIntervalRepository.getChordIntervalsIdsWhereLength( 3L);
            List<Long> chordsNovena = chordIntervalRepository.getChordIntervalsIdsWhereLength( 4L);

            int finalPosGrade = posGrade;
            chordIntervalRepository.getChordWithIntervals( chordsTriada,idTriada, 2L).ifPresentOrElse(chord -> {
                //System.out.println( finalPosGrade + " Triada: " + chord.getSymbol() );
                scaleGradesTriada.add( this.chordToScaleGrade(scale, chord, finalPosGrade, idScaleGrade) );
            }, () -> {
                scaleGradesTriada.add( Symbols.getScaleGradeWithGrade( Symbols.POS_TO_GRADE(finalPosGrade), idScaleGrade) );
                /*System.out.println("Acorde desconocido");*/
            });


            chordIntervalRepository.getChordWithIntervals(chordsSeptima, idSeptima, 3L ).ifPresentOrElse(chord -> {
                //System.out.println( finalPosGrade + " Sewptima: " + chord.getSymbol() );
                scaleGradesSeptima.add( this.chordToScaleGrade(scale, chord, finalPosGrade, idScaleGrade + 1) );
            }, () -> { scaleGradesSeptima.add( Symbols.getScaleGradeWithGrade( Symbols.POS_TO_GRADE(finalPosGrade), idScaleGrade + 1 ) ); /*System.out.println("Acorde desconocido");*/});


            chordIntervalRepository.getChordWithIntervals( chordsNovena, idNovena, 4L ).ifPresentOrElse(chord -> {
                //System.out.println( finalPosGrade + " Novena: " + chord.getSymbol() );
                scaleGradesNovena.add( this.chordToScaleGrade(scale, chord, finalPosGrade, idScaleGrade + 2) );
            }, () -> { scaleGradesNovena.add( Symbols.getScaleGradeWithGrade( Symbols.POS_TO_GRADE(finalPosGrade), idScaleGrade + 2 ) ); /*System.out.println("Acorde desconocido");*/});

            posGrade++;
        }

        scaleGrades.put(idScaleGrade, scaleGradesTriada);
        scaleGrades.put(idScaleGrade + 1, scaleGradesSeptima);
        scaleGrades.put(idScaleGrade + 2, scaleGradesNovena);

        return scaleGrades;
    }


    @Override
    public HashMap<Long, List<ScaleGrade>> generateGradesOfScaleAndSave(Scale scale) {
        HashMap<Long, List<ScaleGrade>> scaleGrades = this.generateGradesOfScale( scale );
        scaleGradeRepository.saveAll(scaleGrades.get(3));
        scaleGradeRepository.saveAll(scaleGrades.get(4));
        scaleGradeRepository.saveAll(scaleGrades.get(5));

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

            if(scaleGrade.getId() != null) grades.put( Symbols.GRADE_TO_POS(scaleGrade.getId().getGrade()), chordDTO );
            else grades.put( -1, chordDTO );
        }

        scaleGradesDTO.setGrades(grades);
        scaleGradesDTO.setId( scale == null ? -1 : scale.getId() );
        scaleGradesDTO.setIdScaleGrade( scaleGrades.get(0).getId() == null ? -1 :scaleGrades.get(0).getId().getId_scale_grade() );
        scaleGradesDTO.setCode(  scale == null ? "" : scale.getCode());
        scaleGradesDTO.setName(  scale == null ? "" : scale.getName());
        scaleGradesDTO.setSymbol ( scale == null ? "" : scale.getSymbol());

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
        return scaleGradeRepository.getGradesOfScale(scale).stream().map( scaleGrade -> {scaleGrade.setExist(true); return scaleGrade;  } ).collect(Collectors.toList());
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

    @Override
    public List<Long> getAllIdsScaleGrades() {
        return scaleGradeRepository.getAllIds();
    }

    @Override
    public List<ScaleGrade> getScaleGradeById(Long id) {
        return scaleGradeRepository.getScaleGradeById(id);
    }

}
