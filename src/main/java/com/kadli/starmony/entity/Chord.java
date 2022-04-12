package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadli.starmony.interfaces.MusicalElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chord implements MusicalElement {

    @Id
    @Column(name = "id_chord")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "code")
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "chordOfInterval")
    private List<ChordIntervals> chordIntervals;

    @JsonIgnore
    @OneToMany(mappedBy = "scaleOfChord")
    private List<ScaleGrade> scaleGrades;

    @JsonIgnore
    @OneToMany(mappedBy = "concreteChord")
    private List<ConcreteChord> concreteChords;

    @Transient
    private final String type = "chord";

    public String getType() { return type; }
}