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

    @Column(name = "id_concrete_chord")
    private Long id_concrete_chord;

    @Column(name = "position_note_chord")
    private int position;
}
