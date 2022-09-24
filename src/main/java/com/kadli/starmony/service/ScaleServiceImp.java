package com.kadli.starmony.service;

import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.repository.ScaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("ScaleService")
public class ScaleServiceImp implements ScaleService{

    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private ModelMapper modelMapper;




    // Utilidades
    public List<Scale> getAllManually(Scale scale){
        Long id = scale.getId();
        String name = scale.getName();
        String symbol = scale.getSymbol();
        String code = scale.getCode();

        List<Scale> scales = new ArrayList<>();
        if(id != null)      this.getById(id).ifPresent(it -> { scales.add(it); });
        if(name != null)    this.getByAttribute("name", name).ifPresent(it -> { scales.add(it); });
        if(symbol != null)  this.getByAttribute("symbol", symbol).ifPresent(it -> { scales.add(it); });
        if(code != null)    this.getByAttribute("code", code).ifPresent(it -> { scales.add(it); });

        return scales;
    }




    // Obtener
    @Override
    public List<Scale> getAll() {
        return scaleRepository.findAll();
    }

    @Override
    public List<Scale> getAll(Scale example) {
        List<Scale> scales = scaleRepository.findAll( Example.of(example) );
        if( scales.isEmpty() ) scales = this.getAllManually(example);
        return scales;}

    @Override
    public Optional<Scale> get(Scale example) {
        Optional<Scale> scale = scaleRepository.findOne(Example.of(example));
        if( !scale.isPresent() ){
            List<Scale> scales = this.getAllManually(example);
            if( !scales.isEmpty() ) scale = Optional.of(scales.get(0));
        }
        return scale;
    }

    @Override
    public Optional<Scale> getById(Long id) {
        return scaleRepository.findById(id);
    }

    @Override
    public Optional<Scale> getByAttribute(String attribute, String value) {
        return scaleRepository.findByAttribute(attribute, value);
    }




    // Guardar
    @Override
    public void save(Scale entity) {
        scaleRepository.save(entity);
    }




    // Eliminar
    @Override
    public void delete(Scale entity) {
        scaleRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        scaleRepository.deleteById(id);
    }

    @Override
    public void deleteByAttribute(String attribute, String value) {
        this.getByAttribute(attribute,value).ifPresent(scale -> {
            scaleRepository.delete(scale);
        });
    }





    // Comprobacion
    @Override
    public boolean exist(Scale entity) {
        boolean existIt = scaleRepository.exists(Example.of(entity));
        if(!existIt) existIt = !this.getAllManually(entity).isEmpty();
        return existIt;
    }

    @Override
    public boolean existById(Long id) {
        return scaleRepository.existsById(id);
    }

    @Override
    public boolean existByAttribute(String attribute, String value) {
        return this.getByAttribute(attribute,value).isPresent();
    }





    // Actualizacion
    @Override
    public void updateAttributeById(Long id, String attribute, String value) {
        this.getById(id).ifPresent(scale -> {
            switch (attribute){
                case "name": scale.setName(value); break;
                case "code": scale.setCode(value); break;
                case "symbol": scale.setSymbol(value); break;
            }
        });
    }





    // Conversiones
    @Override
    public ScaleDTO entityToDTO(Scale entity) {
        return modelMapper.map(entity, ScaleDTO.class);
    }

    @Override
    public Scale dtotoEntity(ScaleDTO dto) {
        return modelMapper.map(dto, Scale.class);
    }


    @Override
    public List<Scale> getScalesWithIntervals(List<Interval> intervals) {
        return scaleRepository.getScalesWithIntervals(intervals);
    }

    @Override
    public List<Scale> getScalesWithChords(List<Chord> chords) {
        return scaleRepository.getScalesWithChords(chords);
    }

    @Override
    public Long getNextId() {
        return scaleRepository.getNextId() + 1;
    }

    @Override
    public List<Scale> getAllByMaxLegth(int max) {
        return scaleRepository.getAllByMaxLength(max);
    }
}
