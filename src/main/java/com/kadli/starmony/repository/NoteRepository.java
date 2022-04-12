package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>{
}
