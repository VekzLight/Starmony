package com.kadli.starmony.dao;

import com.kadli.starmony.models.Chord;

import java.util.List;

public interface ChordDAO {

    List<Chord> getChords();
    public String addChord();

}
