package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.service.ChordService;
import com.kadli.starmony.service.IntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recognize/interval")
@CrossOrigin(origins = "http://localhost:4200")
public class IntervalController {


    @Autowired
    private IntervalService intervalService;

    @GetMapping("/getIntervals")
    public ResponseEntity<List<Interval>> getIntervals(){
        return new ResponseEntity<>(intervalService.getIntervals(), HttpStatus.OK);
    }

    @GetMapping("/getInterval/{id}")
    public ResponseEntity<Optional<Interval>> getInterval(@PathVariable Long id){
        return new ResponseEntity<>(intervalService.getInterval(id), HttpStatus.OK);
    }

}
