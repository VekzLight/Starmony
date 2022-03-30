package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CustomCrudRepository<T, ID extends Serializable> {
    Optional<T> findByAttribute(String attribute, String value);
}
