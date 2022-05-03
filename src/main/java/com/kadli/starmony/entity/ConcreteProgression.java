package com.kadli.starmony.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;

import javax.persistence.*;

@Entity
@Table(name = "concrete_progression")
@Setter
@Getter
@NoArgsConstructor
public class ConcreteProgression {

    @EmbeddedId
    private ConcreteProgressionId id = new ConcreteProgressionId();

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_progression_grade", referencedColumnName = "id_progression_grade"),
            @JoinColumn(name = "position_grade", referencedColumnName = "position_grade")
    })
    private ProgressionGrade progressionGrade;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_concrete_chord", referencedColumnName = "id_concrete_chord"),
            @JoinColumn(name = "position_note_chord", referencedColumnName = "position_note_chord")
    })
    private ConcreteChord concreteChord;

}
