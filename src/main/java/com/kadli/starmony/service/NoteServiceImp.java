package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("NoteService")
public class NoteServiceImp implements NoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ModelMapper modelMapper;


    // Obtener
    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Optional<Note> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Note> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Note> getBySymbol(String symbol) {
        return Optional.empty();
    }

    @Override
    public void save(Note entity) {

    }

    @Override
    public void delete(Note entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void deleteBySymbol(String symbol) {

    }

    @Override
    public void deleteByCode(String code) {

    }

    @Override
    public boolean exist(Note entity) {
        return noteRepository.exists(Example.of(entity));
    }

    @Override
    public boolean existById(Long aLong) {
        return false;
    }

    @Override
    public boolean existByName(String name) {
        return false;
    }

    @Override
    public boolean existBySymbol(String entity) {
        return false;
    }

    @Override
    public boolean existByCode(String code) {
        return false;
    }

    @Override
    public void updateNameById(Long aLong, String name) {

    }

    @Override
    public void updateSymbolById(Long aLong, String symbol) {

    }

    @Override
    public void updateCodeById(Long aLong, String code) {

    }

    @Override
    public NoteDTO entityToDTO(Note entity) {
        return modelMapper.map(entity, NoteDTO.class);
    }

    @Override
    public Note dtotoEntity(NoteDTO dto) {
        return modelMapper.map(dto, Note.class);
    }

}
