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
public class ConcreteIntervalId implements Serializable {

    @Column(name = "interval_id_interval")
    private Long id_interval;

    @Column(name = "note_id_note1")
    private Long id_note1;

    @Column(name = "note_id_note2")
    private Long id_note12;


}
