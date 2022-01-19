package com.kadli.starmony.controllers;

import com.kadli.starmony.dao.ScaleDAOImp;
import com.kadli.starmony.models.Chord;
import com.kadli.starmony.models.Scale;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecognizerController {

    @GetMapping(value = "/recognizeScale")
    public List<Scale> recognizeScale(@RequestBody Scale scale){
        ScaleDAOImp scaleDAO = new ScaleDAOImp();
        return scaleDAO.matchScales(scale);
    }

    @GetMapping(value = "/chordsOfScale")
    public List<Chord> recognizeChordsOfScale(@RequestBody Scale scale){
        return null;
    }

    @GetMapping(value = "/chordsOfScales")
    public List<Chord> recognizeChordsOfScales(@RequestBody List<Scale> scales){
        return null;
    }

}
