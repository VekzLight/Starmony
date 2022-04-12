package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ConcreteChordId implements Serializable {


    @Column(name = "chord_id_chord")
    private Long id_chord;

    @Column(name = "note_id_note")
    private Long id_note;

    @Column(name = "position")
    private int position;

    @Column(name = "id_concrete_chord")
    private Long id_concrete_chord;
}
