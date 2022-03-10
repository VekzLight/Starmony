package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.service.ChordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recognize/chord")
@CrossOrigin(origins = "http://localhost:4200")
public class ChordController {

    @Autowired
    private ChordService chordService;

    @GetMapping("/getChords")
    public ResponseEntity<List<Chord>> getChords(){
        return new ResponseEntity<>(chordService.getChords(), HttpStatus.OK);
    }

    @GetMapping("/getChords/{id}")
    public ResponseEntity<Optional<Chord>> getChords(@PathVariable Long id){
        return new ResponseEntity<>(chordService.getChord(id), HttpStatus.OK);
    }

}
