package com.kadli.starmony.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ConcreteProgressionId implements Serializable {

    @Column(name = "id_concrete_progression")
    private Long id_concrete_progression;

    @Column(name = "position_concrete_chord")
    private int position_concrete_chord;

}
