package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_concrete_interval")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_concrete_interval")
    private Long id_user_concrete_interval;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userOfConcreteIntervals;

    @ManyToOne
    @JoinColumn(name = "id_concrete_interval")
    private ConcreteInterval concreteIntervalsOfUser;

}
