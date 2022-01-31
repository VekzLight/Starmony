package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notes_has_interval")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notes_Has_Intervals {

    @EmbeddedId
    private Notes_Has_Intervals_Id nhi_id = new Notes_Has_Intervals_Id();

    @MapsId("id_note1")
    @ManyToOne
    @JoinColumn(name = "note_id_note1")
    private Note note1;

    @MapsId("id_note2")
    @ManyToOne
    @JoinColumn(name = "note_id_note2")
    private Note note2;

    @MapsId("id_interval")
    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval interval;
}
