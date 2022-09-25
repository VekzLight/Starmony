package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "userOfConcreteIntervals")
    private List<UserInterval> userIntervals;

    @JsonIgnore
    @OneToMany(mappedBy = "userOfConcreteChords")
    private List<UserChord> userChords;

    @JsonIgnore
    @OneToMany(mappedBy = "userOfConcreteScales")
    private List<UserScale> userScales;

    @JsonIgnore
    @OneToMany(mappedBy = "userOfConcreteProgressions")
    private List<UserProgression> userProgressions;
}
