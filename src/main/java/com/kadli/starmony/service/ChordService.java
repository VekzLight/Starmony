package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.entity.Chord;

import java.util.List;
import java.util.Optional;

public interface ChordService extends CustomConcreteService<Chord, Long> {

    // Conversores
    ChordDTO toDTO(Chord chord);
    Chord toEntity(ChordDTO chord);
}
