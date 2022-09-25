package com.kadli.starmony.service;

import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("NoteService")
public class NoteServiceImp implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Utilidades
    public List<Note> getAllManually(Note example){
        Long id = example.getId();
        String name = example.getName();
        String symbol = example.getSymbol();

        List<Note> notes = new ArrayList<>();
        if(id != null) this.getById(id).ifPresent(it -> { notes.add(it); });
        if(name != null) this.getByAttribute("name", name).ifPresent(it -> { notes.add(it); });
        if(symbol != null) this.getByAttribute("symbol", symbol).ifPresent(it -> { notes.add(it); });

        return notes;
    }




    // Obtener
    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getAll(Note example) {
        List<Note> notes = noteRepository.findAll( Example.of(example) );
        if( notes.isEmpty() ) notes = this.getAllManually(example);
        return notes;
    }

    @Override
    public Optional<Note> get(Note example) {
        Optional<Note> note = noteRepository.findOne(Example.of(example));
        if( !note.isPresent() ){
            List<Note> notes = this.getAllManually(example);
            if( !notes.isEmpty() )
                note = Optional.of(notes.get(0));
        }
        return note;
    }

    @Override
    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Optional<Note> getByAttribute(String attribute, String value) {
        Note note = new Note();
        switch (attribute){
            case "name": note.setName(value); break;
            case "symbol": note.setSymbol(value); break;
        }
        return noteRepository.findOne(Example.of(note));
    }


    // Guardar
    @Override
    public void save(Note entity) {
        noteRepository.save(entity);
    }


    // Eliminar
    @Override
    public void delete(Note entity) {
        noteRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public void deleteByAttribute(String attribute, String value) {
        this.getByAttribute(attribute, value).ifPresent(note -> {
            this.delete(note);
        });
    }




    // Comprobacion
    @Override
    public boolean exist(Note entity) {
        boolean existIt = noteRepository.exists(Example.of(entity));
        if(!existIt) existIt = !this.getAllManually(entity).isEmpty();
        return existIt;
    }

    @Override
    public boolean existById(Long id) {
        return noteRepository.existsById(id);
    }

    @Override
    public boolean existByAttribute(String attribute, String value) { return this.getByAttribute(attribute, value).isPresent(); }




    // Actualizar
    @Override
    public void updateAttributeById(Long id, String attribute, String value) {
        this.getById(id).ifPresent(note -> {
            switch (attribute){
                case "name": note.setName(value); break;
                case "symbol": note.setSymbol(value); break;
            }
            this.save(note);
        });
    }




    // Utilidades
    public Long getNoteWithSemitone(Long tonic, int semitones){
        Long id = tonic + semitones;
        while( id > 12 ) id = id - 12;
        return id;
    }





    // Conversiones
    @Override
    public NoteDTO entityToDTO(Note entity) {
        return modelMapper.map(entity, NoteDTO.class);
    }

    @Override
    public Note dtotoEntity(NoteDTO dto) {
        return modelMapper.map(dto, Note.class);
    }

}
