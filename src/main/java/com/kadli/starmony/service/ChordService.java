package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.ConcreteChord;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.repository.ConcreteChordRepository;

import java.util.List;
import java.util.Optional;

public interface ChordService extends CustomCrudService<Chord, Long>, DtoConversions<Chord, ChordDTO>{



}
