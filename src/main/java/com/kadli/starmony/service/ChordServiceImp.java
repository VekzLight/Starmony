package com.kadli.starmony.service;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.repository.ChordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ChordService")
public class ChordServiceImp implements ChordService{

    @Autowired
    private ChordRepository chordRepository;

    @Override
    public List<Chord> getChords() {
        return chordRepository.findAll();
    }

    @Override
    public Optional<Chord> getChord(Long id) {
        return chordRepository.findById(id);
    }

    @Override
    public void saveChord(Chord chord) {
        chordRepository.save(chord);
    }

    @Override
    public void deleteChord(Chord chord) {
        chordRepository.delete(chord);
    }
}
