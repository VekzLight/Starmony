package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "concrete_interval")
@Setter
@Getter
@NoArgsConstructor
public class ConcreteInterval {

    @Id
    @Column(name = "id_concrete_interval")
    private Long id_concrete_interval;

    @ManyToOne
    @JoinColumn(name = "note_id_note1")
    private Note firstNote;

    @ManyToOne
    @JoinColumn(name = "note_id_note2")
    private Note lastNote;

    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval intervalOfNotes;

}
