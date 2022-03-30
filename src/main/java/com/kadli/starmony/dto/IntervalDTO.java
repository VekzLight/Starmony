package com.kadli.starmony.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kadli.starmony.entity.ChordIntervals;
import com.kadli.starmony.entity.ConcreteInterval;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntervalDTO implements Serializable {
    private Long id;
    private String name;
    private String symbol;
    private int semitones;
}
