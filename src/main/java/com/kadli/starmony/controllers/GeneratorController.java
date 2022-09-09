package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.TagProgressionRepository;
import com.kadli.starmony.service.*;
import com.kadli.starmony.utilities.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/generator")
@CrossOrigin(origins = "http://localhost:4200")
public class GeneratorController {


    // Elementos musicales Abstractos
    @Autowired
    private ChordService chordService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private NoteService noteService;


    // Elementos musicales Concretos
    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ProgressionService progressionService;

    @Autowired
    private TagProgressionRepository tagProgressionRepository;

    @Autowired
    private ConcreteProgressionService concreteProgressionService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Genera Acordes Concretos apartir de un acorde y su tonica
     * @param idChord - Acorde Abstracto
     * @param idTonic - Nota Base
     * @return Lista de Acordes Concretos
     */
    @GetMapping("/chord/concrete/{idChord}/tonic/{idTonic}")
    ResponseEntity<Optional<ConcreteChordDTO>> generateConcreteChordWithTonic(@PathVariable Long idChord, @PathVariable Long idTonic){
        List<ConcreteChord> concreteChords = concreteChordService.generateConcreteChords(chordService.getById(idChord).get(), noteService.getById(idTonic).get() );
        if( concreteChords.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        Optional<ConcreteChordDTO> concreteChordDTO = concreteChordService.concreteChordToConcreteChordDTO(concreteChords);
        if( !concreteChordDTO.isPresent() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteChordDTO, HttpStatus.OK);
    }


    /**
     * Genera Los Intervalos de un Acorde
     * @param id - ID del Acorde abstracto en la base de datos
     * @return Lista de intervalos del Acorde
     */
    @GetMapping("/interval/chord/{id}")
    ResponseEntity<List<IntervalDTO>> generateIntervalsOfChord(@PathVariable Long id){
        Optional<Chord> chord = chordService.getById(id);
        if( !chord.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        List<Interval> intervals = intervalService.generateIntervalsOfChord(chord.get());
        if( intervals.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( intervals.stream().map( intervalService::entityToDTO ).collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Genera los Grados de una Escala
     * @param id - ID de la escala en la base de datos
     * @return HashMap de String Grados y Chord acorde
     */
    @GetMapping("/chord/scale/{id}")
    ResponseEntity<List<ScaleGradesDTO>> generateGradesOfScale(@PathVariable Long id){
        Optional<Scale> scale = scaleService.getById(id);
        if( !scale.isPresent() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);

        List<ScaleGradesDTO> scaleGradesDTOS = new ArrayList<>();
        HashMap<Integer, List<ScaleGrade>> scaleGrades = chordService.generateGradesOfScale(scale.get());
        List<ScaleGrade> gradesTriada = scaleGrades.get(3);
        if( gradesTriada.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> gradesSeptima = scaleGrades.get(4);
        if( gradesSeptima.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> gradesNovena = scaleGrades.get(5);
        if( gradesNovena.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        scaleGradesDTOS.add(chordService.scaleGradesToScaleGradeDTO( gradesTriada ));
        scaleGradesDTOS.add(chordService.scaleGradesToScaleGradeDTO( gradesSeptima ));
        scaleGradesDTOS.add(chordService.scaleGradesToScaleGradeDTO( gradesNovena ));

        return new ResponseEntity<>( scaleGradesDTOS , HttpStatus.OK);
    }

    /**
     * Genera los Grados concretos de una Escala concreta
     * @param idConcreteScale - ID de la escala en la base de datos
     * @return HashMap de String Grados y Chord acorde
     */
    @GetMapping("/chord/scale/concrete/{idConcreteScale}")
    ResponseEntity<Optional<ConcreteScaleGradesDTO>> generateConcreteGradesOfScaleWithById(@PathVariable Long idConcreteScale){
        List<ConcreteScale> scale = concreteScaleService.getCompleteConcreteScaleById( idConcreteScale );
        if( scale.isEmpty() ) return new ResponseEntity(new Message(-1,"Empty"), HttpStatus.NOT_FOUND);


        HashMap<Integer, List<ScaleGrade>> scaleGrades =  chordService.generateGradesOfScale( scale.get(0).getScaleOfNotes() );
        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.generateConcreteGradesOfScale( scale , scaleGrades);
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        Optional<ConcreteScaleGradesDTO> concreteScaleGradeDTO = concreteChordService.concreteScaleGradesToConcreteScaleGradesDTO(concreteScaleGrades);
        if( !concreteScaleGradeDTO.isPresent())return new ResponseEntity(new Message(-1,"Could Not Generate"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteScaleGradeDTO , HttpStatus.OK);
    }



    // Progressiones
    @GetMapping("/progression/{idProgression}/scale/{idScale}/simple")
    ResponseEntity<Optional<ProgressionGradeDTO>> generateSimpleProgressionGradeOfScale(@PathVariable Long idProgression, @PathVariable Long idScale){
        Optional<Progression> progression = progressionService.getById(idProgression);
        if( !progression.isPresent())  return new ResponseEntity(new Message(-1,"Progression Not Fount"), HttpStatus.NOT_FOUND);

        Optional<Scale> scale = scaleService.getById(idScale);
        if( !scale.isPresent())  return new ResponseEntity(new Message(-1,"Scale Not Found"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale.get());
        if( scaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Scale Grades not Found"), HttpStatus.NOT_FOUND);

        List<ProgressionGrade> progressionGrades = progressionService.generateProgressionGradeSimple( progression.get(), scaleGrades );
        if( scale.isEmpty() ) return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);

        Optional<ProgressionGradeDTO>  progressionGradeDTO = progressionService.progressionGradeToProgressionGradeDTO( progressionGrades );

        return new ResponseEntity<>( progressionGradeDTO , HttpStatus.OK);
    }

    @GetMapping("/progression/{idProgression}/scale/{idScale}/force")
    ResponseEntity<Optional<ProgressionGradeDTO>> generateForceProgressionGradeOfScale(@PathVariable Long idProgression, @PathVariable Long idScale){
        Optional<Progression> progression = progressionService.getById(idProgression);
        if( !progression.isPresent())  return new ResponseEntity(new Message(-1,"Progression Not Fount"), HttpStatus.NOT_FOUND);

        Optional<Scale> scale = scaleService.getById(idScale);
        if( !scale.isPresent())  return new ResponseEntity(new Message(-1,"Scale Not Found"), HttpStatus.NOT_FOUND);

        List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale.get());
        if( scaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Scale Grades not Found"), HttpStatus.NOT_FOUND);

        List<ProgressionGrade> progressionGrades = progressionService.generateProgressionGradeForce( progression.get(), scaleGrades );
        if( scale.isEmpty() ) return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);


        return new ResponseEntity<>( progressionService.progressionGradeToProgressionGradeDTO(progressionGrades) , HttpStatus.OK);
    }

    @GetMapping("/progression/concrete/{idProgressionGrade}/s/concrete/{idConcreteScale}")
    ResponseEntity<Optional<ConcreteProgressionDTO>> generateConcreteProgressionWithTonic(@PathVariable Long idProgressionGrade, @PathVariable Long idConcreteScale){
        List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeById(idProgressionGrade);
        if( progressionGrades.isEmpty())  return new ResponseEntity(new Message(-1,"Progressions Grades Not Found"), HttpStatus.NOT_FOUND);

        List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( idConcreteScale );
        if( concreteScaleGrades.isEmpty() ) return new ResponseEntity(new Message(-1,"Note Grades Not Found"), HttpStatus.NOT_FOUND);

        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById( idConcreteScale );
        List<ConcreteProgression> concreteProgressions = concreteProgressionService.generateConcreteProgression( progressionGrades, concreteScaleGrades, concreteScales);
        if( concreteProgressions.isEmpty())  return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);

        Optional<ConcreteProgressionDTO> concreteProgressionDTO = concreteProgressionService.concreteProgressionToConcreteProgressionDTO(concreteProgressions);

        return new ResponseEntity<>( concreteProgressionDTO , HttpStatus.OK);
    }


    @PostMapping(path = "/chord/concrete/ia",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ConcreteChordDTO>> getNextConcreteChord(@RequestBody Long[] ids){

        Map<String, Long> map = new HashMap<>();
        map.put("idConcreteChord", ids[0]);
        map.put("idConcreteScale", ids[1]);
        map.put("position", ids[2]);

        List<ConcreteChord> _concreteChords = concreteChordService.getCompleteConcreteChordById(map.get("idConcreteChord"));
        List<ConcreteScaleGrade> _concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId(map.get("idConcreteScale"));

        String url = "http://localhost:5000/api/chord/predict/{idConcreteChord}/{idConcreteScale}/{position}";
        String response = this.restTemplate.getForObject(url, String.class, map);

        Long idConcreteChord = Long.parseLong( response.substring(1, response.length() - 1) );
        Long cercania = Long.MAX_VALUE;
        Long idCercano = idConcreteChord;
        for(ConcreteScaleGrade it: _concreteScaleGrades){
            Long idConcrete = it.getConcreteChord().getCc_id().getId_concrete_chord();
            Long bufferCercania = Math.abs(idConcrete - idConcreteChord);
            if (  bufferCercania < cercania){
                cercania = bufferCercania;
                idCercano = idConcrete;
            } else if (idConcrete == idConcreteChord) break;
        }


        List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordById(  idCercano );
        if( concreteChords.isEmpty() ) return new ResponseEntity(new Message(-1,"No se pudo generar"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( concreteChordService.concreteChordToConcreteChordDTO(concreteChords), HttpStatus.OK );
    };


    /**
     * Genera y Guarda los intervalos de todos los acordes en la base de datos
     * @return
     */
    @GetMapping("/interval/chord/save")
    ResponseEntity<Message> generateAllIntervalsOfChordAndSave(){
        intervalService.generateAllIntervalsOfChordsAndSave();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda las escalas concretas de todas las escalas abstractas en la base de datos
     * @return Mensaje de exito
     * @return
     */
    @GetMapping("/scale/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteScales(){
        concreteScaleService.generateAllConcreteScalesAndSave();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda los intervalos concretos de todos los intervalos abstractos en la base de datos
     * @return
     */
    @GetMapping("/interval/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteIntervals(){
        concreteIntervalService.generateAllConcreteIntervalsAndSave();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda los acordes concretos de todos los acordes en la base de datos
     * @return Mensaje de exito
     */
    @GetMapping("/chord/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteChords(){
        concreteChordService.generateAndSaveAllConcretechords();
        return new ResponseEntity<>(new Message(1, "Done"), HttpStatus.OK);
    }



    /**
     * Genera y Guarda los grados de la escala de todas las escalas en la base de datos
     * @return Mensaje de exito
     */
    @GetMapping("/chord/scale/save")
    ResponseEntity<Message> generateAndSaveAllGradesOfScales(){
        chordService.generateAllGradesOfScaleAndSave();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }

    /**
     * Genera y Guarda los grados de la escala de todas las escalas en la base de datos
     * @return Mensaje de exito
     */
    @GetMapping("/chord/scale/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteGradesOfScales(){
        concreteChordService.generateAndSaveAllConcreteGradesOfScale();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }


    @GetMapping("/progression/scale/save")
    ResponseEntity<Message> generateAndSaveAllProgressionsSimpleOfScale(){
        progressionService.generateAndSaveAllProgresionGradeSimple();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }

    @GetMapping("/progression/concrete/save")
    ResponseEntity<Message> generateAndSaveAllConcreteProgressions(){
        concreteProgressionService.generateAndSaveAllConcreteProgressions();
        return new ResponseEntity<>( new Message(1, "Done"), HttpStatus.OK);
    }

    @GetMapping("/progression/concrete/scale/concrete/{idConcreteScale}/tag/{idTag}")
    ResponseEntity<List<ConcreteProgressionDTO>> generateConcreteProgressionByTagAndScale(@PathVariable Long idConcreteScale, @PathVariable Long idTag){
        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById(idConcreteScale);
        System.out.println(concreteScales.isEmpty());
        Scale scale = concreteScales.get(0).getScaleOfNotes();
        Long idScaleGrade = chordService.getGradesOfScale(scale).get(0).getId().getId_scale_grade();

        List<ConcreteProgressionDTO> concreteProgressionDTOS = new ArrayList<>();

        List<Long> idProgressions = tagProgressionRepository.getIdProgressionsOfIdTag(idTag);
        for(Long idProgression: idProgressions){

            Long idProgressionGrade = progressionService.getCompleteProgressionGradeByScaleGrade(idProgression,idScaleGrade).get(0).getId().getId_progression_grade();
            List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionsByProgressionGradeAndConcreteScale(idConcreteScale, idProgressionGrade);
            concreteProgressionDTOS.add( concreteProgressionService.concreteProgressionToConcreteProgressionDTO(concreteProgressions).get() );
        }

        return new ResponseEntity<>( concreteProgressionDTOS, HttpStatus.OK);
    }



    private ScaleGenerated generateDataOfScale(Scale scale){
        ScaleGenerated scaleGenerated = new ScaleGenerated();

        List<Note> notes = noteService.getAll();
        HashMap<Long, List<ConcreteScale>> concreteScales = new HashMap<>();
        HashMap<Integer, List<ScaleGrade>> scaleGrades = new HashMap<>();
        List<ScaleInterval> scaleIntervals = new ArrayList<>();
        HashMap<String, List<ConcreteScaleGrade>> concreteScaleGradeHashMap = new HashMap<>();
        HashMap<Long, List<ProgressionGrade>> progressionGradeHashMap = new HashMap<>();
        HashMap<String, List<ConcreteProgression>> concreteProgressionsHashMap = new HashMap<>();

        // Generacion de Escalas Concretas
        System.out.print("Generando Escalas Concretas: ");
        for(Note note: notes)
            concreteScales.put(note.getId(), concreteScaleService.generateCompleteConcreteScales( scale, note));
        System.out.println(concreteScales.size());

        for(List<ConcreteScale> concreteScale: concreteScales.values()){
            for(ConcreteScale it: concreteScale)
                System.out.print(it.getNotesOfScale().getSymbol() + " - ");
            System.out.println("");
        } System.out.println("\n");

        // Generacion de Intervalos de la Escala
        System.out.print("Generando Intervalos de la escala: ");
        scaleIntervals.addAll(intervalService.generateIntervalsOfScale( scale ));
        System.out.println(scaleIntervals.size());
        for(ScaleInterval scaleInterval: scaleIntervals){
            System.out.println(scaleInterval.getIntervalOfScale().getName());
        } System.out.println("\n");

        // Generacion de Grados de Escala Abstractos
        System.out.print("Generando Grados Abstractos de la escala: ");
        scaleGrades.putAll(chordService.generateGradesOfScale( scale ));
        System.out.println(scaleGrades.size());
        for(int key: scaleGrades.keySet()){
            for(ScaleGrade it: scaleGrades.get(key))
                System.out.print(it.getChordOfScale().getSymbol() + " - ");
            System.out.println("");
        } System.out.println("\n");


        // Generacion de Grados Concretos de Escala
        System.out.println("Generando Grados concretos de la escalas: ");
        for(Note note: notes){
            concreteScaleGradeHashMap.putAll(concreteChordService.generateConcreteGradesOfScales( concreteScales.get(note.getId()), scaleGrades ));
        }

        for( String key : concreteScaleGradeHashMap.keySet() ){
            System.out.print( key + " : "  );
            for(ConcreteScaleGrade concreteScaleGrade: concreteScaleGradeHashMap.get(key)){
                if ( concreteScaleGrade.getScaleGrade().getChordOfScale().getId() != -1 ) {
                    if (concreteScaleGrade.getId().getPosition_note_chord() == 1)
                        System.out.print(concreteScaleGrade.getConcreteChord().getNote().getSymbol() + "" + concreteScaleGrade.getConcreteChord().getConcreteChord().getSymbol() + " - ");
                } else System.out.print( "Desconocido - " );
            }
            System.out.println("");
        } System.out.println("");



        System.out.print("Generando Progresiones de la escala: ");
        // Generacion de Progressiones de la Escala
        List<Progression> progressions = progressionService.getAllWithLenth( concreteScales.size() );

        for (Progression progression: progressions){
            List<ProgressionGrade> progressionGrades = progressionService.generateProgressionGradeSimple( progression, scaleGrades.get(3) );
            if(!progressionGrades.isEmpty()) {
                progressionGradeHashMap.put( progression.getId(), progressionGrades );
            }
        }


        System.out.print("Generando Progresiones concretas de la escala: ");
        // Generacion de Progresiones concretas de la escala
        for(Note note: notes){
            List<ConcreteScaleGrade> concreteScaleGrades = concreteScaleGradeHashMap.get(3+","+note.getId());
            for(Progression progression: progressions){
                List<ProgressionGrade> progressionGrades = progressionGradeHashMap.get(progression.getId());
                if(progressionGrades != null){
                    List<ConcreteProgression> concreteProgressions = concreteProgressionService.generateConcreteProgression(progressionGrades, concreteScaleGrades, concreteScales.get(note.getId()));
                    concreteProgressionsHashMap.put(note.getId()+","+progression.getId(), concreteProgressions);
                    System.out.println("[" + note.getId()+"," + progression.getId()+" : "+concreteProgressions.size()+"]");
                }
                else{
                    System.out.println( progression );
                }
            }
        }

        scaleGenerated.setScale(scale);
        scaleGenerated.setConcreteScales( concreteScales );
        scaleGenerated.setScaleIntervals( scaleIntervals );
        scaleGenerated.setScaleGrades( scaleGrades );
        scaleGenerated.setConcreteScaleGrades( concreteScaleGradeHashMap );
        scaleGenerated.setProgressionGrades( progressionGradeHashMap );
        scaleGenerated.setConcreteProgressions( concreteProgressionsHashMap );

        return scaleGenerated;
    }

    private boolean createConcreteProgressions(Progression progression) {
        List<Scale> scales =scaleService.getAll();
        List<Note> notes = noteService.getAll();

        boolean flag = false;
        for(Scale scale:scales){
            for (Note note: notes){
                Long concreteScaleId = concreteScaleService.getIdConcreteScale(scale.getId(), note.getId());
                List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById( concreteScaleId );
                List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( concreteScaleId );

                Long scaleGradeId = chordService.getIdScaleGrade(scale);
                List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeByScaleGrade(progression.getId(), scaleGradeId);

                List<ConcreteProgression> concreteProgressions = concreteProgressionService.generateAndSaveConcreteProgression(progressionGrades, concreteScaleGrades, concreteScales );
                if(!concreteProgressions.isEmpty())  flag = true;
            }
        }
        return flag;
    }

    private boolean createProgressionGrades(Progression progression) {
        List<Scale> scales = scaleService.getAll();
        boolean flag = false;
        for( Scale scale: scales ){
            List<ScaleGrade> scaleGrades = chordService.getGradesOfScale(scale);
            List<ProgressionGrade> progressionGrades = progressionService.generateAndSaveProgressionGradeSimple( progression, scaleGrades );
            if(!progressionGrades.isEmpty()) flag = true;
        }
        return flag;
    }

    private boolean postCreatedProgression(Progression progression) {
        boolean probe1 = createProgressionGrades(progression);
        System.out.println("Progression Grades " + probe1);
        boolean probe2 = createConcreteProgressions(progression);
        System.out.println("ConcreteProgressions " + probe2);
        return probe1 && probe2;
    }

    private boolean probeCode(String code){
        boolean isNumeric;
        try{
             isNumeric =  code.matches("[+-]?\\d*(\\.\\d+)?");
        } catch( Exception ex ){ return false; }

        return isNumeric;
    }

    @PostMapping(path = "/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewScale(@RequestBody ScaleDTO scaleDTO){
        // Convierte su codigo por uno valido
        scaleDTO.setCode(scaleDTO.getCode().replaceAll("-", Symbols.SYMBOL_SEPARATION_SCALE));


        // Obtiene su version entidad
        Scale scale = scaleService.dtotoEntity(scaleDTO);

/*  Sato de comprobacion para pruebas
        // Comprueba que existe en la base de datos
        if( scaleService.exist( scale ) ) {
            scale = scaleService.get( scale ).get();
            return new ResponseEntity<>( new Message( 2, scale.getId() + "") , HttpStatus.OK );
        }
*/

        // Comprueba los campos
        if( this.probeCode(scale.getCode()) ) return new ResponseEntity( new Message(-1, "Codigo no valido"), HttpStatus.OK );
        if( scale.getName()   == null ) return new ResponseEntity( new Message(-1, "Nombre no valido"), HttpStatus.OK );
        if( scale.getSymbol() == null ) return new ResponseEntity( new Message(-1, "Simbolo no valido"), HttpStatus.OK );


        // Genera informacion de la escala
        ScaleGenerated scaleGenerated = this.generateDataOfScale( scale );

        // Guarda la escala en la base de datos

        return new ResponseEntity<>( scaleGenerated, HttpStatus.OK);
    }

    @PostMapping(path = "/progression",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewProgression(@RequestBody ProgressionDTO progressionDTO){
        Optional<Progression> progression = Optional.of(progressionService.dtotoEntity(progressionDTO));
        if( progressionService.exist( progression.get() ) ) {
            progression = progressionService.get( progression.get() );
            return new ResponseEntity<>( new Message( 1, progression.get().getId() + ""),HttpStatus.OK);
        }

        if( progression.get().getCode() == null &&
                progression.get().getName() == null &&
                progression.get().getSymbol() == null)
            return new ResponseEntity( new Message(-1, "Progression no valida"), HttpStatus.NOT_ACCEPTABLE );

        progressionService.save(progression.get());

        Optional<Progression> response = progressionService.get( progression.get() );
        if( !response.isPresent() )  return new ResponseEntity( new Message(-1, "Progression no guardada"), HttpStatus.NOT_FOUND );
        if( !this.postCreatedProgression( response.get() ) ) return new ResponseEntity( new Message(-1, "Progression no relevante"), HttpStatus.NOT_ACCEPTABLE );


        return new ResponseEntity<>( new Message( 1, response.get().getId() + ""),HttpStatus.OK);
    }


}
