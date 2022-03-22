package com.kadli.starmony.entity;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "concrete_chord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcreteChord {

    @EmbeddedId
    private ConcreteChordId cc_id = new ConcreteChordId();

    @MapsId("id_chord")
    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chord_interval;


    @MapsId("id_interval")
    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval interval_chord;
}
