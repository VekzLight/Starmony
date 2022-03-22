package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Progression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressionRepository extends JpaRepository<Progression, Long>, ProgressionRepositoryCustom {
}
