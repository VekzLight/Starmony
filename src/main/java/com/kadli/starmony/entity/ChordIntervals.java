package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "chord_interval")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChordIntervals {

    @EmbeddedId
    private ChordIntervalsId cc_id = new ChordIntervalsId();

    @MapsId("id_chord")
    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chord_interval;

    @MapsId("id_interval")
    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval interval_chord;
}
