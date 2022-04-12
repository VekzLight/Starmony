package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ChordIntervalRepository;
import com.kadli.starmony.repository.ChordRepository;
import com.kadli.starmony.repository.ConcreteChordRepository;
import com.kadli.starmony.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ChordService")
public class ChordServiceImp implements ChordService {

    // Repositorios de la clase
    @Autowired
    private ChordRepository chordRepository;

    // Herramientas
    @Autowired
    private ModelMapper modelMapper;




    // Obtener
    @Override
    public List<Chord> getAll() {
        return chordRepository.findAll();
    }

    @Override
    public Optional<Chord> getById(Long id) {
        return chordRepository.findById(id);
    }

    @Override
    public Optional<Chord> getByName(String name) {
        return chordRepository.findByAttribute("name", name);
    }

    @Override
    public Optional<Chord> getByCode(String code) {
        return chordRepository.findByAttribute("code", code);
    }

    @Override
    public Optional<Chord> getBySymbol(String symbol) {
        return chordRepository.findByAttribute("symbol", symbol);
    }





    // Guardar
    @Override
    public void save(Chord chord) {
        chordRepository.save(chord);
    }





    // Eliminar
    @Override
    public void delete(Chord chord) {
        chordRepository.delete(chord);
    }

    @Override
    public void deleteById(Long id) {
        chordRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        this.getByName(name).ifPresent(chord -> {
            chordRepository.delete(chord);
        });
    }

    @Override
    public void deleteBySymbol(String symbol) {
        this.getBySymbol(symbol).ifPresent(chord -> {
            chordRepository.delete(chord);
        });
    }

    @Override
    public void deleteByCode(String code) {
        this.getByCode(code).ifPresent(chord -> {
            chordRepository.delete(chord);
        });
    }






    // Comprobacion

    @Override
    public boolean exist(Chord chord) {
        return chordRepository.exists(Example.of(chord));
    }

    @Override
    public boolean existById(Long id) {
        return chordRepository.existsById(id);
    }

    @Override
    public boolean existByName(String name) {
        return chordRepository.exists(Example.of(Chord.builder().name(name).build()));
    }

    @Override
    public boolean existBySymbol(String symbol) {
        return chordRepository.exists(Example.of(Chord.builder().symbol(symbol).build()));
    }

    @Override
    public boolean existByCode(String code) {
        return chordRepository.exists(Example.of(Chord.builder().code(code).build()));
    }





    // Actializar

    @Override
    public void updateNameById(Long id, String name) {
        this.getById(id).ifPresent(chord -> {
            chord.setName(name);
            this.save(chord);
        });
    }

    @Override
    public void updateSymbolById(Long id, String symbol) {
        this.getById(id).ifPresent(chord -> {
            chord.setSymbol(symbol);
            this.save(chord);
        });
    }

    @Override
    public void updateCodeById(Long id, String code) {
        this.getById(id).ifPresent(chord -> {
            chord.setCode(code);
            this.save(chord);
        });
    }








    // Conversiones
    @Override
    public ChordDTO entityToDTO(Chord entity) {
        return modelMapper.map(entity, ChordDTO.class);
    }

    @Override
    public Chord dtotoEntity(ChordDTO dto) {
        return modelMapper.map(dto, Chord.class);
    }

}
