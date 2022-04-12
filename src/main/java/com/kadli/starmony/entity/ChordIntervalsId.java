package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ChordIntervalsId implements Serializable {

    @Column(name = "interval_id_interval")
    private Long id_interval;

    @Column(name = "chord_id_chord")
    private Long id_chord;


}
