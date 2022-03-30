package com.kadli.starmony.service;

import com.kadli.starmony.entity.Progression;
import com.kadli.starmony.repository.ProgressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ProgressionService")
public class ProgressionServiceImp implements ProgressionService{

    @Autowired
    private ProgressionRepository progressionRepository;


    @Override
    public List<Progression> getConcrete() {
        return null;
    }

    @Override
    public Optional<Progression> getConcreteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> getConcreteByName(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> getConcreteByCode(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> getConcreteBySymbol(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Progression> getAll() {
        return null;
    }

    @Override
    public Optional<Progression> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> getBySymbol(String symbol) {
        return Optional.empty();
    }

    @Override
    public void save(Progression entity) {

    }

    @Override
    public void delete(Progression entity) {

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
    public boolean exist(Progression entity) {
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
    public void updateName(Progression entity, String name) {

    }

    @Override
    public void updateCode(Progression entity, String code) {

    }

    @Override
    public void updateSymbol(Progression entity, String symbol) {

    }
}
