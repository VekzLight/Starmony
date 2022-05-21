package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_concrete_scale")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScale {

    @Id
    @Column(name = "id_user_concrete_scale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user_concrete_scale;


    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userOfConcreteScales;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_concrete_scale", referencedColumnName = "id_concrete_scale"),
            @JoinColumn(name = "position_note_scale", referencedColumnName = "position_note_scale")
    })
    private ConcreteScale concreteScalesOfUser;


}
