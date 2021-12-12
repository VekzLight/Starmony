package com.kadli.starmony.controllers;

import com.kadli.starmony.models.Chord;
import com.kadli.starmony.dao.ChordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChordController {

    @Autowired
    private ChordDAO chordDao;

    @RequestMapping(value = "chords")
    public List<Chord> getChords(){
        return chordDao.getChords();
    }

    @RequestMapping(value = "prueba")
    public String getPrueba(){
        return chordDao.addChord();
    }
}
