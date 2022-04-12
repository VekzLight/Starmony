package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.ConcreteScale;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.repository.ScaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("ScaleService")
public class ScaleServiceImp implements ScaleService{

    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Scale> getAll() {
        return scaleRepository.findAll();
    }

    @Override
    public Optional<Scale> getById(Long id) {
        return scaleRepository.findById(id);
    }

    @Override
    public Optional<Scale> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Scale> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Scale> getBySymbol(String symbol) {
        return Optional.empty();
    }

    @Override
    public void save(Scale entity) {

    }

    @Override
    public void delete(Scale entity) {

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
    public boolean exist(Scale entity) {
        return scaleRepository.exists(Example.of(entity));
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
    public ScaleDTO entityToDTO(Scale entity) {
        return modelMapper.map(entity, ScaleDTO.class);
    }

    @Override
    public Scale dtotoEntity(ScaleDTO dto) {
        return modelMapper.map(dto, Scale.class);
    }

}
