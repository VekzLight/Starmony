package com.kadli.starmony.controllers;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.service.*;
import com.kadli.starmony.utilities.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;

    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ConcreteProgressionService concreteProgressionService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrarUsuario(@RequestBody UserDTO userDTO){
        User user = userService.dtotoEntity(userDTO);
        if(user.getUsername() == null) return new ResponseEntity( new Message(-1, "No valido"), HttpStatus.OK);
        if(user.getEmail() == null) return new ResponseEntity( new Message(-1, "No valido"), HttpStatus.OK);
        if(user.getPassword() == null) return new ResponseEntity( new Message(-1, "No valido"), HttpStatus.OK);

        if(userService.exist(user)){
            return new ResponseEntity( new Message(-1, "El usuario ya existe"), HttpStatus.OK);
        }

        user.setId(null);
        User userNew = userService.registrar(user);
        if(userNew == null) return new ResponseEntity( new Message(-1, "Usuario no creado"), HttpStatus.OK);

        String tokenJwt = jwtUtil.create(String.valueOf(userNew.getId()), userNew.getEmail());
        return new ResponseEntity<>( new Message(1,tokenJwt), HttpStatus.OK );
    };

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> iniciarSession(@RequestBody UserDTO userDTO){
        User user = userService.dtotoEntity(userDTO);

        User userVerify = userService.obtenerUsuarioPorCredenciales(user);
        if(userVerify == null) return new ResponseEntity<>( new Message(-1, "Usuario o Contraseña no validos"), HttpStatus.OK);

        String tokenJwt = jwtUtil.create(String.valueOf(userVerify.getId()), userVerify.getEmail());

        String idUser = jwtUtil.getKey(tokenJwt);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        return new ResponseEntity<>( new Message(1,tokenJwt), HttpStatus.OK);
    };

    @PostMapping(path = "/session",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSession(@RequestBody String token){
        String validity = jwtUtil.getKey(token);
        if(validity == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);
        return new ResponseEntity<>( new Message(1, "Session Valida"), HttpStatus.OK);
    };

    @PostMapping(path = "/interval",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addIntervals(@RequestHeader(value = "authorization") String token, @RequestBody Long interval){
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);
        User user = userService.getById(Long.valueOf(idUser)).get();
        System.out.println(user.getId() + " : " +interval);
        ConcreteInterval concreteInterval = concreteIntervalService.getConcreteIntervalById(interval).get();

        UserInterval response = userService.saveInterval(user, concreteInterval);

        return new ResponseEntity<>( new Message(1, response.getConcreteIntervalsOfUser().getId_concrete_interval() + ""), HttpStatus.OK);
    };

    @GetMapping("/interval")
    public ResponseEntity<List<ConcreteIntervalDTO>> getAllConcreteIntervals(@RequestHeader(value = "authorization") String token) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();

        List<UserInterval> userIntervals = userService.getConcreteIntervalsOf(user.getId());

        List<ConcreteInterval> concreteIntervals = new ArrayList<>();
        for(UserInterval userInterval: userIntervals)   concreteIntervals.add( userInterval.getConcreteIntervalsOfUser() );

        return new ResponseEntity<>( concreteIntervalService.concreteIntervalsToConcreteIntervalDTOS(concreteIntervals), HttpStatus.OK);
    }

    @DeleteMapping("/interval/{id}")
    public ResponseEntity<?> deleteConcreteInterval(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        userService.eliminarIntervalo(user.getId(), id);

        return new ResponseEntity<>( new Message(1, "Eliminado con Exito"), HttpStatus.OK);
    }


    @PostMapping(path = "/chord",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addChord(@RequestHeader(value = "authorization") String token, @RequestBody Long idChord){
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordById(idChord);

        UserChord response = userService.saveChord(user, concreteChords.get(0));

        return new ResponseEntity<>( new Message(1, response.getConcreteChordsOfUser().getCc_id().getId_concrete_chord() + ""), HttpStatus.OK);
    };

    @GetMapping("/chord")
    public ResponseEntity<List<ConcreteChordDTO>> getAllConcreteChords(@RequestHeader(value = "authorization") String token) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        List<UserChord> userChords = userService.getConcreteChordsOf(user.getId());

        List<ConcreteChordDTO> concreteChordsDTO = new ArrayList<>();
        for(UserChord userChord: userChords){
            List<ConcreteChord> buffer = concreteChordService.getCompleteConcreteChordById(userChord.getConcreteChordsOfUser().getCc_id().getId_concrete_chord());
            concreteChordsDTO.add( concreteChordService.concreteChordToConcreteChordDTO(buffer).get() );
        }

        return new ResponseEntity<>( concreteChordsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/chord/{id}")
    public ResponseEntity<?> deleteConcreteChord(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        userService.deleteChord(user.getId(), id);

        return new ResponseEntity<>( new Message(1, "Eliminado con Exito"), HttpStatus.OK);
    }


    @PostMapping(path = "/scale",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addScale(@RequestHeader(value = "authorization") String token, @RequestBody Long idScale){
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById(idScale);

        UserScale response = userService.saveScale(user, concreteScales.get(0));

        return new ResponseEntity<>( new Message(1, response.getConcreteScalesOfUser().getId().getId_concrete_scale() + ""), HttpStatus.OK);
    };

    @GetMapping("/scale")
    public ResponseEntity<List<ConcreteScaleDTO>> getAllConcreteScales(@RequestHeader(value = "authorization") String token) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        List<UserScale> userScales = userService.getConcreteScalesOf(user.getId());

        List<ConcreteScaleDTO> concreteScaleDTOS = new ArrayList<>();
        for(UserScale userScale: userScales){
            List<ConcreteScale> buffer = concreteScaleService.getCompleteConcreteScaleById(userScale.getConcreteScalesOfUser().getId().getId_concrete_scale());
            concreteScaleDTOS.add( concreteScaleService.concreteScalesToConcreteScaleDTO(buffer) );
        }

        return new ResponseEntity<>( concreteScaleDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/scale/{id}")
    public ResponseEntity<?> deleteConcreteScale(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        userService.deleteScale(user.getId(), id);

        return new ResponseEntity<>( new Message(1, "Eliminado con Exito"), HttpStatus.OK);
    }

    @PostMapping(path = "/progression",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProgression(@RequestHeader(value = "authorization") String token, @RequestBody Long idProgression){
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        List<ConcreteProgression> concreteProgressions = concreteProgressionService.getCompleteConcreteProgressionById(idProgression);

        UserProgression response = userService.saveProgression(user, concreteProgressions.get(0));

        return new ResponseEntity<>( new Message(1, response.getConcreteProgressionsOfUser().getId().getId_concrete_progression() + ""), HttpStatus.OK);
    };

    @GetMapping("/progression")
    public ResponseEntity<List<ConcreteProgressionDTO>> getAllConcreteProgressions(@RequestHeader(value = "authorization") String token) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        List<UserProgression> userProgressions = userService.getConcreteProgressionsOf(user.getId());

        List<ConcreteProgressionDTO> concreteProgressionDTOS = new ArrayList<>();
        for(UserProgression userProgression: userProgressions){
            List<ConcreteProgression> buffer = concreteProgressionService.getCompleteConcreteProgressionById(userProgression.getConcreteProgressionsOfUser().getId().getId_concrete_progression());
            concreteProgressionDTOS.add( concreteProgressionService.concreteProgressionToConcreteProgressionDTO(buffer).get() );
        }

        return new ResponseEntity<>( concreteProgressionDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/progression/{id}")
    public ResponseEntity<?> deleteConcreteProgression(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        String idUser = jwtUtil.getKey(token);
        if(idUser == null) new ResponseEntity<>( new Message(-1, "Sesion invalida"), HttpStatus.OK);

        User user = userService.getById(Long.valueOf(idUser)).get();
        userService.deleteProgression(user.getId(), id);

        return new ResponseEntity<>( new Message(1, "Eliminado con Exito"), HttpStatus.OK);
    }
}
