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
public class ChordInterval {

    @EmbeddedId
    private ChordIntervalId id = new ChordIntervalId();

    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chordOfInterval;

    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval intervalOfChord;
}
