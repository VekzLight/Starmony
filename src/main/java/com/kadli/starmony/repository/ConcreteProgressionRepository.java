package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteProgression;
import com.kadli.starmony.entity.ConcreteProgressionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConcreteProgressionRepository extends JpaRepository<ConcreteProgression, ConcreteProgressionId> {

    @Query(" SELECT MAX( cp.id.id_concrete_progression ) FROM ConcreteProgression cp")
    Long getLastId();
}
