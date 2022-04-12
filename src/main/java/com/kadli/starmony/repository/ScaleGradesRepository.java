package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ScaleGrade;
import com.kadli.starmony.entity.ScaleGradesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScaleGradesRepository extends JpaRepository<ScaleGrade, ScaleGradesId> {
}
