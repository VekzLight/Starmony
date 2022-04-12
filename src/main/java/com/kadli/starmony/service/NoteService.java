package com.kadli.starmony.service;

import com.kadli.starmony.dto.NoteDTO;
import com.kadli.starmony.entity.Note;

public interface NoteService extends CustomCrudService<Note, Long>, DtoConversions<Note, NoteDTO>{

}
