package com.kadli.starmony.repository;

import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.Optional;

public interface CustomCrudRepository<T, ID extends Serializable> {
    Optional<T> findByAttribute(String attribute, String value);
}
