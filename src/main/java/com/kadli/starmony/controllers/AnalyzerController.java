package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analyzer")
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyzerController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private ProgressionService progressionService;


    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ConcreteProgressionService concreteProgressionService;

    // Intervals
    @PostMapping(path = "/interval/chord",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getIntervalsOfChord(@RequestBody ChordDTO chordDto){
        Chord chord = chordService.dtotoEntity(chordDto);
        if( !chordService.exist(chord) ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.getIntervalsOfChord(chord);
        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/concrete/chord/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervalsOfChordConcrete(@RequestBody ConcreteChordDTO concreteChordDTO){
        Optional<Chord> chord = chordService.get( Chord.builder().code(concreteChordDTO.getCode()).build() );
        Note tonic = noteService.dtotoEntity(concreteChordDTO.getTonic());

        if( !chord.isPresent() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        List<Interval> intervals = intervalService.getIntervalsOfChord(chord.get());

        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        for(Interval interval: intervals)
            concreteIntervals.add( concreteIntervalService.getConcreteIntervalWithTonic(interval.getId(), tonic.getId()).get() );


        if( concreteIntervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( concreteIntervals.stream().map( concreteIntervalService::concreteIntervalToConcreteIntervalDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/scale/tonic",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getIntervalsOfScale(@RequestBody ScaleDTO scaleDto){
        Scale scale = scaleService.dtotoEntity(scaleDto);
        if( !scaleService.exist(scale) ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.getIntervalsOfScaleByTonic( scale );
        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/scale/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getAllIntervalsOfScale(@RequestBody ScaleDTO scaleDto){
        Scale scale = scaleService.dtotoEntity(scaleDto);
        if( !scaleService.exist(scale) ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.getIntervalsOfScaleByAll( scale );
        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/concrete/scale/tonic",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervalsOfScale(@RequestBody ConcreteScaleDTO concreteScaleDto){
        List<Note> notes = concreteScaleDto.getNotes().values().stream().map( noteService::dtotoEntity ).collect(Collectors.toList());
        Note tonic = noteService.dtotoEntity(concreteScaleDto.getTonic());

        List<ConcreteIntervalDTO> concreteIntervals = concreteIntervalService.getConcreteIntervalsWithNotesAndTonic(tonic.getId(), notes.stream().map( note-> note.getId() ).collect(Collectors.toList()));

        return new ResponseEntity<>( concreteIntervals, HttpStatus.OK );
    };

    @PostMapping(path = "/interval/note",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDTO>> getIntervalsOfNotes(@RequestBody List<NoteDTO> notesDto){
        List<Note> notes = notesDto.stream().map( noteService::dtotoEntity).collect(Collectors.toList());
        List<Interval> intervals = intervalService.getIntervalsOfNotes( notes );

        if( intervals.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };





    // Chords
    @PostMapping(path = "/chord/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChordDTO>> getChordsWithIntervals(@RequestBody List<IntervalDTO> intervalsDto){
        List<Interval> intervals = intervalsDto.stream().map( intervalService::dtotoEntity ).collect(Collectors.toList());
        List<Chord> chords = chordService.getChordsWithIntervals(intervals);
        if( chords.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( chords.stream().map( chordService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/chord/concrete/interval/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteChordDTO>> getConcreteChordsWithInterval(@RequestBody ConcreteIntervalDTO concreteIntervalDTO){
        Optional<Interval> interval = intervalService.getById( concreteIntervalDTO.getId() );
        List<Interval> intervals = new ArrayList<>();
        intervals.add(interval.get());

        List<Chord> chords = chordService.getChordsWithIntervals(intervals);
        List<ConcreteChordDTO> concreteChordDTOS = new ArrayList<>();
        for(Chord chord: chords){
            List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordWithTonic(chord.getId(), concreteIntervalDTO.getFirsNote().getId());
            concreteChordDTOS.add( concreteChordService.concreteChordToConcreteChordDTO(concreteChords).get() );
        }
        return new ResponseEntity<>( concreteChordDTOS, HttpStatus.OK );
    };

    @PostMapping(path = "/chord/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScaleGradesDTO> getGradesOfScale(@RequestBody ScaleDTO scaleDto){
        Scale scale = scaleService.dtotoEntity(scaleDto);
        List<ScaleGrade> grades = chordService.getGradesOfScale(scale);
        if( grades.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( chordService.scaleGradesToScaleGradeDTO(grades), HttpStatus.OK );
    };

    @PostMapping(path = "/chord/concrete/scale/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ConcreteScaleGradesDTO>> getConcreteGradesOfScale(@RequestBody ConcreteScaleDTO concreteScaleDTO){
        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( concreteScaleDTO.getId_concrete_scale() );
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        Optional<ConcreteScaleGradesDTO> concreteScaleGradeDTO = concreteChordService.concreteScaleGradesToConcreteScaleGradesDTO(concreteScaleGrades);
        if( !concreteScaleGradeDTO.isPresent() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteScaleGradeDTO, HttpStatus.OK );
    };

    @PostMapping(path = "/chord/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteChordDTO>> getConcreteChordsWithME(@RequestBody ConcreteChordMEDTO concreteChordMEDTO){
        List<Long> idChords     = new ArrayList<>(concreteChordMEDTO.getChord());
        List<Long> idIntervals  = new ArrayList<>(concreteChordMEDTO.getIntervals());
        List<Long> idNotes      = new ArrayList<>(concreteChordMEDTO.getNotes());
        Long idTonic = concreteChordMEDTO.getTonic();

        List<ConcreteChordDTO> concreteChordDTOS = new ArrayList<>();
        List<Chord> chords = new ArrayList<>();

        if(!idChords.isEmpty()){
            for(Long idChord: idChords)
                chords.add( chordService.getById(idChord).get() );
        }

        if(!idIntervals.isEmpty()){
            List<Interval> intervals = new ArrayList<>();
            for(Long idInterval: idIntervals)
                intervals.add( intervalService.getById(idInterval).get() );

            chords.addAll(chordService.getChordsWithIntervals(intervals));
        }

        if(chords.isEmpty() && idIntervals.isEmpty()){
            if(idNotes.isEmpty())   return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            concreteChordDTOS = concreteChordService.getConcreteChordsWithNotesAndTonic(idNotes, idTonic);
        } else {
            if(idNotes.isEmpty())   return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);

            for(Chord chord: chords.stream().distinct().collect(Collectors.toList())){
                List<ConcreteChord> bufferConcreteChord = concreteChordService.getCompleteConcreteChordWithTonic(chord.getId(), idTonic);

                int count = 0;
                for(ConcreteChord it: bufferConcreteChord)
                    if( idNotes.contains(it.getNote().getId()) )
                        count++;

                if(count == idNotes.size())
                    concreteChordDTOS.add( concreteChordService.concreteChordToConcreteChordDTO(bufferConcreteChord).get() );
            }
        }

        return new ResponseEntity<>( concreteChordDTOS.stream().distinct().collect(Collectors.toList()), HttpStatus.OK );
    };


    @PostMapping(path = "/scale/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScalesWithME(@RequestBody ConcreteScaleMEDTO concreteScaleMEDTO){
        List<Long> idChords = new ArrayList<>(concreteScaleMEDTO.getChords());
        List<Long> idScales = new ArrayList<>(concreteScaleMEDTO.getScales());
        List<Long> idNotes = new ArrayList<>(concreteScaleMEDTO.getNotes());
        Long idTonic = concreteScaleMEDTO.getTonic();

        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();

        List<Scale> scales = new ArrayList<>();

        if(!idScales.isEmpty()){
            for(Long idScale: idScales)
                scales.add( scaleService.getById(idScale).get() );
        }

        if(!idChords.isEmpty()){
            List<Chord> chord = new ArrayList<>();
            for(Long idChord: idChords)
                chord.add( chordService.getById(idChord).get() );

            scales.addAll(scaleService.getScalesWithChords(chord));
        }

        if(idScales.isEmpty() && idChords.isEmpty()){
            // Solo notas
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            concreteScaleDTOS = concreteScaleService.getConcreteScalesWithTonicAndNotes(idNotes, idTonic);
        } else {
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);

            for(Scale scale: scales.stream().distinct().collect(Collectors.toList())){
                List<ConcreteScale> bufferConcreteScale = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), idTonic);

                int count = 0;
                for(ConcreteScale it: bufferConcreteScale)
                    if( idNotes.contains(it.getNotesOfScale().getId() ))
                        count++;

                if(count == idNotes.size() + 1)
                    concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(bufferConcreteScale));
            }
        }

        return new ResponseEntity<>( concreteScaleDTOS.stream().distinct().collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/interval/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteIntervalDTO>> getConcreteIntervalsWithME(@RequestBody ConcreteIntervalMEDTO concreteIntervalMEDTO){
        List<Long> intervalDTOS = concreteIntervalMEDTO.getIntervals();
        List<Long> noteDTOS = concreteIntervalMEDTO.getNotes();
        Long tonicId = concreteIntervalMEDTO.getTonic();

        List<ConcreteIntervalDTO> concreteIntervalDTOS = new ArrayList<>();

        if(intervalDTOS.isEmpty()){
            if(noteDTOS.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            concreteIntervalDTOS = concreteIntervalService.getConcreteIntervalsWithNotesAndTonic(tonicId, noteDTOS);
        } else {
            if(noteDTOS.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);


            for(Long interval: intervalDTOS){
                concreteIntervalDTOS.add( concreteIntervalService.concreteIntervalToConcreteIntervalDTO(concreteIntervalService.getConcreteIntervalWithTonic(interval, tonicId).get()));
            }
            concreteIntervalDTOS.addAll(concreteIntervalService.getConcreteIntervalsWithNotesAndTonic(tonicId, noteDTOS));
        }

        return new ResponseEntity<>( concreteIntervalDTOS.stream().distinct().collect(Collectors.toList()), HttpStatus.OK );
    };



    @PostMapping(path = "/progression/concrete/me",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteProgressionDTO>> getConcreteProgressionWithProgressionScaleAndChord(@RequestBody ConcreteProgressionMEDTO concreteProgressionMEDTO){
        Long idTonic = concreteProgressionMEDTO.getIdTonic();
        List<Long> idNotes = new ArrayList<>(concreteProgressionMEDTO.getIdNotes());
        List<Long> idScales = new ArrayList<>(concreteProgressionMEDTO.getIdScales());
        List<Long> idChords = new ArrayList<>(concreteProgressionMEDTO.getIdChords());
        List<Long> idProgressions   = new ArrayList<>(concreteProgressionMEDTO.getIdProgressions());

        List<ConcreteProgressionDTO> concreteProgressionDTOS = new ArrayList<>();

        List<Scale> scales = new ArrayList<>();
        List<Chord> chords = new ArrayList<>();
        List<Progression> progressions = new ArrayList<>();

        if(!idScales.isEmpty()){
            for(Long idScale: idScales)
                scales.add( scaleService.getById(idScale).get() );
        }

        if(!idChords.isEmpty()){
            for(Long idChord: idChords){
                chords.add( chordService.getById(idChord).get() );
            }

            scales.addAll(scaleService.getScalesWithChords(chords));
        }

        if(!idProgressions.isEmpty()){
            for(Long id: idProgressions)
                progressions.add( progressionService.getById(id).get() );
        }

        if(idScales.isEmpty() && idChords.isEmpty()){
            // Solo notas
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);
            List<ConcreteScaleDTO> concreteScales = concreteScaleService.getConcreteScalesWithTonicAndNotes(idNotes,idTonic);
            List<Long> idConcreteScales = concreteScales.stream().map(concreteScaleDTO -> concreteScaleDTO.getId_concrete_scale()).collect(Collectors.toList());

            List<ConcreteChordDTO> concreteChords = concreteChordService.getConcreteChordsWithNotesAndTonic(idNotes, idTonic);
            List<Long> idConcreteChords = concreteChords.stream().map(concreteChordDTO -> concreteChordDTO.getId_concrete_chord()).collect(Collectors.toList());

            concreteProgressionDTOS = concreteProgressionService.getCompleteConcreteProgressionsByIdScaleAndChord(idConcreteScales, idConcreteChords, idTonic);
        } else {
            if(idNotes.isEmpty()) return new ResponseEntity(new Message(-1,"No hay Elementos"), HttpStatus.NOT_ACCEPTABLE);

            List<Long> idConcreteScales = new ArrayList<>();
            List<Long> idConcreteChords = new ArrayList<>();

            List<Scale> scalesresponsive = new ArrayList<>();
            if(idScales.isEmpty()) scalesresponsive = scales.stream().distinct().collect(Collectors.toList());
            else {
                for(Long idScale: idScales)
                    scalesresponsive.add( scaleService.getById(idScale).get() );
            }

            for(Scale scale: scalesresponsive){
                List<ConcreteScale> bufferConcreteScale = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), idTonic);
                List<ConcreteScaleGrade> buffesScaleGrade = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( bufferConcreteScale.get(0).getId().getId_concrete_scale() );

                int countChords = 0;
                for(ConcreteScaleGrade it: buffesScaleGrade){
                    if( idChords.contains(it.getConcreteChord().getConcreteChord().getId()) ) countChords++;
                }

                int countNotes = 0;
                boolean flag = false;
                for(ConcreteScale it: bufferConcreteScale){
                    if( idNotes.contains(it.getNotesOfScale().getId() )) countNotes++;
                    if( it.getId().getPosition() == 1 && it.getNotesOfScale().getId() == idTonic ){
                        System.out.println(it.getNotesOfScale().getSymbol() + " - " + it.getNotesOfScale().getId() + ":" + idTonic);
                        flag = true;
                    }
                }

                if(countNotes > idNotes.size() && countChords >= idChords.size() && flag){
                    idConcreteChords.addAll(buffesScaleGrade.stream().map(concreteScaleGrade -> concreteScaleGrade.getConcreteChord().getCc_id().getId_concrete_chord()).collect(Collectors.toList()));
                    idConcreteScales.add( bufferConcreteScale.get(0).getId().getId_concrete_scale() );
                }
            }

            concreteProgressionDTOS.addAll(concreteProgressionService.getCompleteConcreteProgressionsByIdScaleAndChord( idConcreteScales.stream().distinct().collect(Collectors.toList()), idConcreteChords.stream().distinct().collect(Collectors.toList()), idTonic));
        }

        List<ConcreteProgressionDTO> response = new ArrayList<>();

        if(!idProgressions.isEmpty()){
            for(ConcreteProgressionDTO concreteProgressionDTO: concreteProgressionDTOS)
                if( idProgressions.contains( concreteProgressionDTO.getId() ) ) response.add( concreteProgressionDTO );
        } else response = concreteProgressionDTOS;


        return new ResponseEntity<>( response, HttpStatus.OK );
    };

    // Scale
    @PostMapping(path = "/scale/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScaleDTO>> getScalesWithIntervals(@RequestBody List<IntervalDTO> intervalDTOS){
        List<Interval> intervals = intervalDTOS.stream().map( intervalService::dtotoEntity ).collect(Collectors.toList());
        List<Scale> scales = scaleService.getScalesWithIntervals(intervals);
        if( scales.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( scales.stream().map( scaleService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    // Scale
    @PostMapping(path = "/scale/concrete/interval/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScalesWithConcreteIntervals(@RequestBody ConcreteIntervalDTO concreteIntervalDTO){
        Interval interval = intervalService.getById(concreteIntervalDTO.getId()).get();
        List<Interval> intervals = new ArrayList<>();
        intervals.add(interval);

        List<Scale> scales = scaleService.getScalesWithIntervals(intervals);
        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();
        for(Scale scale: scales){
            List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleWithTonic(scale.getId(), concreteIntervalDTO.getFirsNote().getId());
            concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales) );
        }

        return new ResponseEntity<>( concreteScaleDTOS, HttpStatus.OK );
    };

    @PostMapping(path = "/scale/concrete/progression/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConcreteScaleDTO> getConcreteScalesWithConcreteProgression(@RequestBody ConcreteProgressionDTO concreteProgressionDTO){
        List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionById(concreteProgressionDTO.getId_concrete_progression());
        Long idConcreteScale = concreteProgressions.get(0).getConcreteScale().getId().getId_concrete_scale();

        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById(idConcreteScale);

        return new ResponseEntity<>( concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales), HttpStatus.OK );
    };


    @PostMapping(path = "/scale/chord",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScaleDTO>> getScalesWithChord(@RequestBody List<ChordDTO> chordsDTO){
        List<Chord> chords = chordsDTO.stream().map(chordService::dtotoEntity).collect(Collectors.toList());
        List<Scale> scales = scaleService.getScalesWithChords(chords);
        if( scales.isEmpty() ) return new ResponseEntity( new Message(-1, "No encontrados") , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( scales.stream().map( scaleService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK );
    };

    @PostMapping(path = "/scale/concrete/chord/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConcreteScaleDTO>> getConcreteScalesWithConcreteChord(@RequestBody ConcreteChordDTO concreteChordDTOS){
        List<Long> idConcreteChords = new ArrayList<>();
        idConcreteChords.add(concreteChordDTOS.getId_concrete_chord());
        List<Long> idConcreteScales = concreteScaleService.getIdConcreteScalesWithConcreteChords(idConcreteChords);

        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();
        for(Long idConcreteScale: idConcreteScales){
            List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId(  idConcreteScale);

            int count = 0;
            for(ConcreteScaleGrade concreteScaleGrade: concreteScaleGrades)
                if( idConcreteChords.contains(concreteScaleGrade.getConcreteChord().getCc_id().getId_concrete_chord()) ) count++;

            if(count >= idConcreteChords.size()){
                List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById( idConcreteScale );
                concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(concreteScales) );
            }
        }
        return new ResponseEntity<>( concreteScaleDTOS, HttpStatus.OK );
    };


    // Progression Grades
    @PostMapping(path = "/progression/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ProgressionGradeDTO>> getProgressionGradesOfScale(@RequestBody ProgressionScaleDTO progressionScaleDTO){
        Scale scale = scaleService.dtotoEntity(progressionScaleDTO.getScaleDTO());
        Progression progression = progressionService.dtotoEntity(progressionScaleDTO.getProgressionDTO());

        if( !scaleService.exist(scale) ) return new ResponseEntity( new Message(-1, "Escala no encotrada") , HttpStatus.NOT_FOUND);
        if( !progressionService.exist(progression) ) return new ResponseEntity( new Message(-1, "Progression encontrada") , HttpStatus.NOT_FOUND);

        Long idScaleGrade = chordService.getIdScaleGrade(scale);

        List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeByScaleGrade( progression.getId(), idScaleGrade );
        if( progressionGrades.isEmpty() ) return new ResponseEntity( new Message(-1, "No existen los grados de la progression") , HttpStatus.NOT_FOUND);

        Optional<ProgressionGradeDTO> progressionGradeDTO = progressionService.progressionGradeToProgressionGradeDTO(progressionGrades);
        System.out.println(progressionGradeDTO.get().getId_progression_grade());

        return new ResponseEntity<>( progressionGradeDTO, HttpStatus.OK );
    };

    @PostMapping(path = "/progression/concrete/scale/concrete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgressionGradeDTO> getProgressionGradesOfConcreteScale(@RequestBody ConcreteProgressionMEDTO concreteProgressionMEDTO){
        Long idTonic = concreteProgressionMEDTO.getIdTonic();
        Long idProgression = concreteProgressionMEDTO.getIdProgressions().get(0);
        Scale scale = scaleService.getById(concreteProgressionMEDTO.getIdScales().get(0)).get();

        Long idScaleGrade = chordService.getIdScaleGrade(scale);
        Long idConcreteScale = concreteScaleService.getIdConcreteScale(scale.getId(), idTonic);

        Long idProgressionGrade = progressionService.getIdProgressionGradeByScaleGrade(idProgression, idScaleGrade);

        List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionsByProgressionGradeAndConcreteScale(idConcreteScale, idProgressionGrade);


        return new ResponseEntity<>( concreteProgressionService.concreteProgressionToConcreteProgressionDTO(concreteProgressions).get(), HttpStatus.OK );
    };


}