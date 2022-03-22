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
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "symbol")
    protected String symbol;

    @Column(name = "code")
    protected String code;

    @JsonIgnore
    @OneToMany(mappedBy = "chord_interval")
    protected List<ConcreteChord> concreteChords;

    @JsonIgnore
    @OneToMany(mappedBy = "scale_chord")
    protected List<ScaleGrades> scaleGrades;

    @Transient
    protected final String type = "chord";

    public String getType() { return type; }
}