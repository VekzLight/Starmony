package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.ConcreteChord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChordRepository extends JpaRepository<Chord, Long>, ChordRepositoryCustom {

}
