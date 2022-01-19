package com.kadli.starmony.dao;

import com.kadli.starmony.models.Chord;

import java.util.List;

public interface ChordDAO {

    public List<Chord> getChords();
    public String addChord();

}
