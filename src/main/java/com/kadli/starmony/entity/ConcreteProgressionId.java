package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ConcreteProgressionId implements Serializable {

    @Column(name = "chord_id_chord")
    private Long id_chord;

    @Column(name = "progression_id_progression")
    private Long id_progression;

}