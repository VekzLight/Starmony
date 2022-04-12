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

    @Column(name = "scale_id_scale")
    private Long id_scale;

    @Column(name = "note_id_note")
    private Long id_note;

    @Column(name = "id_concrete_scale")
    private Long id_concrete_scale;

    @Column(name = "position")
    private int position;
}
