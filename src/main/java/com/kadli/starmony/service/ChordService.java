package com.kadli.starmony.service;

import com.kadli.starmony.entity.Chord;

import java.util.List;
import java.util.Optional;

public interface ChordService {

    List<Chord> getChords();
    Optional<Chord> getChord(Long id);
    void saveChord(Chord chord);
    void deleteChord(Chord chord);

}
