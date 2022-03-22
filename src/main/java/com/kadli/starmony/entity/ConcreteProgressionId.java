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
public class ConcreteProgressionId implements Serializable {

    @Column(name = "chord_id_chord")
    private Long id_chord;

    @Column(name = "progression_id_progression")
    private Long id_progression;

}