package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord concreteChord;

    @ManyToOne
    @JoinColumn(name = "note_id_note")
    private Note note;

}
