package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadli.starmony.interfaces.MusicalElement;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "_interval")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Interval implements MusicalElement {

    @Id
    @Column(name = "id_interval")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "symbol")
    protected String symbol;

    @Column(name = "semitones")
    protected int semitones;

    @Transient
    protected final String type = "interval";

    @JsonIgnore
    @OneToMany(mappedBy = "intervalOfNotes")
    protected List<ConcreteInterval> concreteIntervals;

    @JsonIgnore
    @OneToMany(mappedBy = "intervalOfChord")
    protected List<ChordInterval> chordIntervals;

    @Override
    public String getType() { return type; }
}