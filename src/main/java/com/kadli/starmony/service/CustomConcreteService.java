package com.kadli.starmony.service;

import com.kadli.starmony.entity.Chord;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CustomConcreteService<T, ID extends Serializable> extends CustomCrudService<T, ID>{

    List<T> getConcrete();
    Optional<T> getConcreteById(Long id);
    Optional<T> getConcreteByName(Long id);
    Optional<T> getConcreteByCode(Long id);
    Optional<T> getConcreteBySymbol(Long id);

}
