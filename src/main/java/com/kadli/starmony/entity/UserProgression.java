package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_concrete_progression")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProgression {

    @Id
    @Column(name = "id_user_concrete_progression")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user_concrete_progression;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userOfConcreteProgressions;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_concrete_progression", referencedColumnName = "id_concrete_progression"),
            @JoinColumn(name = "position_concrete_chord", referencedColumnName = "position_concrete_chord")
    })
    private ConcreteProgression concreteProgressionsOfUser;

}
