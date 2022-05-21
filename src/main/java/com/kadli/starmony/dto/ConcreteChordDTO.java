package com.kadli.starmony.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteChordDTO extends ChordDTO implements Serializable {
    private Long id_concrete_chord;
    private NoteDTO tonic;
    private List<NoteDTO> notes;
}
