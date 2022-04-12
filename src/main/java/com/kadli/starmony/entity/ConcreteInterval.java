package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "concrete_interval")
@Setter
@Getter
@NoArgsConstructor
public class ConcreteInterval {

    @EmbeddedId
    private ConcreteIntervalId id = new ConcreteIntervalId();

    @MapsId("id_note1")
    @ManyToOne
    @JoinColumn(name = "note_id_note1")
    private Note firstNote;

    @MapsId("id_note2")
    @ManyToOne
    @JoinColumn(name = "note_id_note2")
    private Note lastNote;

    @MapsId("id_interval")
    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval intervalOfNotes;
}
