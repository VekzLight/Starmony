package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.repository.ChordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ChordService")
public class ChordServiceImp implements ChordService {

    @Autowired
    private ChordRepository chordRepository;

    @Autowired
    private ModelMapper modelMapper;




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
        return chordRepository.findByAttribute("name",name);
    }

    @Override
    public Optional<Chord> getByCode(String code) {
        return chordRepository.findByAttribute("code",code);
    }

    @Override
    public Optional<Chord> getBySymbol(String symbol) {
        return chordRepository.findByAttribute("symbol",symbol);
    }




    @Override
    public void save(Chord chord) {
        chordRepository.save(chord);
    }




    @Override
    public void delete(Chord chord) {
        chordRepository.delete(chord);
    }

    @Override
    public void deleteById(Long id) {chordRepository.deleteById(id);}

    @Override
    public void deleteByName(String name) {
        this.getByName(name).ifPresent( chord -> {
            chordRepository.deleteById(chord.getId());
        });
    }

    @Override
    public void deleteBySymbol(String symbol) {
        this.getBySymbol(symbol).ifPresent( chord -> {
            chordRepository.deleteById(chord.getId());
        });
    }

    @Override
    public void deleteByCode(String code) {
        this.getByCode(code).ifPresent( chord -> {
            chordRepository.deleteById(chord.getId());
        });
    }




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




    @Override
    public void updateNameById(Long id, String name) {
        chordRepository.findById(id).ifPresent(chord -> {
            chord.setName(name);
            chordRepository.save(chord);
        });
    }

    @Override
    public void updateSymbolById(Long id, String symbol) {
        chordRepository.findById(id).ifPresent(chord -> {
            chord.setSymbol(symbol);
            chordRepository.save(chord);
        });
    }

    @Override
    public void updateCodeById(Long id, String code) {
        chordRepository.findById(id).ifPresent(chord -> {
            chord.setCode(code);
            chordRepository.save(chord);
        });
    }

    @Override
    public void updateName(Chord chord, String name) {
        if (chordRepository.exists(Example.of(chord))){
            chord.setName(name);
            chordRepository.save(chord);
        }
    }

    @Override
    public void updateCode(Chord chord, String code) {
        if (chordRepository.exists(Example.of(chord))){
            chord.setName(code);
            chordRepository.save(chord);
        }
    }

    @Override
    public void updateSymbol(Chord chord, String symbol) {
        if (chordRepository.exists(Example.of(chord))){
            chord.setName(symbol);
            chordRepository.save(chord);
        }
    }




    @Override
    public List<Chord> getConcrete() {
        return null;
    }

    @Override
    public Optional<Chord> getConcreteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Chord> getConcreteByName(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Chord> getConcreteByCode(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Chord> getConcreteBySymbol(Long id) {
        return Optional.empty();
    }



    @Override
    public ChordDTO toDTO(Chord chord) {
        return modelMapper.map(chord, ChordDTO.class);
    }

    @Override
    public Chord toEntity(ChordDTO chord) {
        return null;
    }


}
