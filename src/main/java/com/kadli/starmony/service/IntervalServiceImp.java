package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.IntervalDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.repository.IntervalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("IntervalService")
public class IntervalServiceImp implements IntervalService{

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Interval> getConcrete() {
        return null;
    }

    @Override
    public Optional<Interval> getConcreteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getConcreteByName(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getConcreteByCode(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getConcreteBySymbol(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Interval> getAll() {
        return null;
    }

    @Override
    public Optional<Interval> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> getBySymbol(String symbol) {
        return Optional.empty();
    }

    @Override
    public void save(Interval entity) {

    }

    @Override
    public void delete(Interval entity) {

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
    public boolean exist(Interval entity) {
        return false;
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
    public void updateName(Interval entity, String name) {

    }

    @Override
    public void updateCode(Interval entity, String code) {

    }

    @Override
    public void updateSymbol(Interval entity, String symbol) {

    }



    @Override
    public IntervalDTO toDTO(Interval interval) {
        return modelMapper.map(interval, IntervalDTO.class);
    }

    @Override
    public List<Interval> getIntervalsOfScale(Scale scale) {
        return intervalRepository.getIntervalsOfScale(scale);
    }

    @Override
    public List<Interval> getAllIntervalsOfScale(Scale scale) {
        return intervalRepository.getAllIntervalsOfScale(scale);
    }
}
