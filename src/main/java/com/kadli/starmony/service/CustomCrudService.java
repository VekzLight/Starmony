package com.kadli.starmony.service;

import com.kadli.starmony.entity.ProgressionGrade;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.entity.ScaleInterval;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CustomCrudService<T, ID extends Serializable>{

    // Obtener
    List<T> getAll();
    List<T> getAll(T example);
    List<T> getAllManually(T example);
    Optional<T> get(T example);
    Optional<T> getById(ID id);
    Optional<T> getByAttribute(String attribute, String value);

    // Guardar
    void save(T entity);

    // Eliminar
    void delete(T entity);
    void deleteById(ID id);
    void deleteByAttribute(String attribute, String value);


    // Comprobaciones
    boolean exist(T entity);
    boolean existById(ID id);
    boolean existByAttribute(String attribute, String value);

    // Modificaciones

    void updateAttributeById(ID id, String attribute, String value);


}
