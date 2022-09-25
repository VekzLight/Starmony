package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ChordIntervalId implements Serializable {

    @Column(name = "id_chord_interval")
    private Long id_chord_interval;

    @Column(name = "position_interval")
    private Long position_interval;


}
