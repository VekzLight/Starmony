package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_concrete_chord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChord {

    @Id
    @Column(name = "id_user_concrete_chord")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user_concrete_chord;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userOfConcreteChords;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_concrete_chord", referencedColumnName = "id_concrete_chord"),
            @JoinColumn(name = "position_note_chord", referencedColumnName = "position_note_chord")
    })
    private ConcreteChord concreteChordsOfUser;


}
