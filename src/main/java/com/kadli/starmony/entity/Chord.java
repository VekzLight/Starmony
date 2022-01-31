package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadli.starmony.interfaces.MusicalElement;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(mappedBy = "chords")
    protected List<Interval> intervals;

    @JsonIgnore
    @OneToMany(mappedBy = "scale")
    protected List<Scale_Grades> scale_grades;

    @Transient
    protected final String type = "chord";

    public String getType() { return type; }
}