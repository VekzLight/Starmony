package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteIntervalDTO extends IntervalDTO implements Serializable {
    private NoteDTO firsNote;
    private NoteDTO lastNote;
}
