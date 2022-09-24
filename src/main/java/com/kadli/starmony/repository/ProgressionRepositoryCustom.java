package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Progression;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgressionRepositoryCustom extends CustomCrudRepository<Progression, Long>{

    List<Progression> getAllWithLenth(int size);

    Long getMaxId();
}
