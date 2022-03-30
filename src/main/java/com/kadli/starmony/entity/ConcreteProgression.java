package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "concrete_progression")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcreteProgression {

    @EmbeddedId
    private ConcreteProgressionId concreteProgressionId = new ConcreteProgressionId();

    @MapsId("id_chord")
    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chord_progression;

    @MapsId("id_progression")
    @ManyToOne
    @JoinColumn(name = "progression_id_progression")
    private Progression progression_chord;

    @JoinColumn(name = "grade")
    private String grade;
}
