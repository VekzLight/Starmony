package com.kadli.starmony.repository;

import com.kadli.starmony.entity.UserChord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserChordRepository extends JpaRepository<UserChord, Long> {

    @Query("FROM UserChord uc INNER JOIN uc.userOfConcreteChords u WHERE u.id = :idUser")
    List<UserChord> getAllChordsOfUserId(@Param("idUser") Long id);

    @Query("SELECT MAX(uc.id_user_concrete_chord) FROM UserChord uc")
    Long getLast();

    @Query("SELECT uc.id_user_concrete_chord FROM UserChord uc INNER JOIN uc.concreteChordsOfUser cc INNER JOIN uc.userOfConcreteChords u WHERE u.id = :idUser AND cc.id.id_concrete_chord = :idConcreteChord")
    Long getConcreteIdByChordId(@Param("idUser") Long idUser, @Param("idConcreteChord") Long idConcreteChord);
}
