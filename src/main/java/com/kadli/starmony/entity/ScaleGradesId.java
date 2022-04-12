package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ScaleGradesId implements Serializable {

    @Column(name = "scale_id_scale")
    private Long id_scale;

    @Column(name = "chord_id_chord")
    private Long id_chord;

}
