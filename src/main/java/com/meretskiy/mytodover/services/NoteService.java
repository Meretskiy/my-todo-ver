package com.meretskiy.mytodover.services;

import com.meretskiy.mytodover.dto.NoteDto;
import com.meretskiy.mytodover.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> findAllNotesByOwnerName(String username);

    Note createNote(String userName);

    Note findNote(String username, Long id);

    Note updateNote(String username, NoteDto noteDto);

    void deleteNote(String username, Long id);
}
