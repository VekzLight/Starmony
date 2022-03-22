package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "concrete_interval")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcreteInterval {

    @EmbeddedId
    private ConcreteIntervalId concreteIntervalId = new ConcreteIntervalId();

    @MapsId("id_note1")
    @ManyToOne
    @JoinColumn(name = "note_id_note1")
    private Note note1_interval;

    @MapsId("id_note2")
    @ManyToOne
    @JoinColumn(name = "note_id_note2")
    private Note note2_interval;

    @MapsId("id_interval")
    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval interval_notes;
}
