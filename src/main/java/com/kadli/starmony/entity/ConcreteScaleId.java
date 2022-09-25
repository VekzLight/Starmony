package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ConcreteScaleId implements Serializable {

    @Column(name = "id_concrete_scale")
    private Long id_concrete_scale;

    @Column(name = "position_note_scale")
    private int position;
}
