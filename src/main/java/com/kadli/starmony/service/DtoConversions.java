package com.kadli.starmony.service;

import java.util.List;

public interface DtoConversions<T, D> {
    D entityToDTO(T entity);
    T dtotoEntity(D dto);
}
