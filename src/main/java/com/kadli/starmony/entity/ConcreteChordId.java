package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcreteChordId implements Serializable {

    @Column(name = "interval_id_interval")
    private Long id_interval;

    @Column(name = "chord_id_chord")
    private Long id_chord;

}
