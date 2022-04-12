package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.entity.Chord;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CustomCrudService<T, ID extends Serializable>{

    // Obtener
    List<T> getAll();
    Optional<T> getById(ID id);
    Optional<T> getByName(String name);
    Optional<T> getByCode(String code);
    Optional<T> getBySymbol(String symbol);


    // Guardar
    void save(T entity);

    // Eliminar
    void delete(T entity);
    void deleteById(ID id);
    void deleteByName(String name);
    void deleteBySymbol(String symbol);
    void deleteByCode(String code);


    // Comprobaciones
    boolean exist(T entity);
    boolean existById(ID id);
    boolean existByName(String name);
    boolean existBySymbol(String entity);
    boolean existByCode(String code);


    // Modificaciones

    void updateNameById(ID id, String name);
    void updateSymbolById(ID id, String symbol);
    void updateCodeById(ID id, String code);

}
