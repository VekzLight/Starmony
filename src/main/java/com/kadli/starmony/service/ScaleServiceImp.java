package com.kadli.starmony.service;

import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.repository.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("ScaleService")
public class ScaleServiceImp implements ScaleService{

    @Autowired
    private ScaleRepository scaleRepository;

    @Override
    public List<Scale> getConcrete() {
        return null;
    }

    @Override
    public Optional<Scale> getConcreteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Scale> getConcreteByName(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Scale> getConcreteByCode(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Scale> getConcreteBySymbol(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Scale> getAll() {
        return null;
    }

    @Override
    public Optional<Scale> getById(Long aLong) {
        return Optional.empty();
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
    public void updateName(Scale entity, String name) {

    }

    @Override
    public void updateCode(Scale entity, String code) {

    }

    @Override
    public void updateSymbol(Scale entity, String symbol) {

    }
}
