package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadli.starmony.interfaces.MusicalElement;
import lombok.*;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "intervals")
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
    @ManyToMany
    @JoinTable(
            name = "interval_has_chord",
            joinColumns = @JoinColumn(name = "interval_id_interval"),
            inverseJoinColumns = @JoinColumn(name = "chord_id_chord")
    )
    protected List<Chord> chords;

    @JsonIgnore
    @OneToMany(mappedBy = "note1")
    protected List<Notes_Has_Intervals> notes_has_intervals;

    @Override
    public String getType() { return type; }
}
