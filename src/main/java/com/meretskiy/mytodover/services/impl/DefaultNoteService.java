package com.meretskiy.mytodover.services.impl;

import com.meretskiy.mytodover.dto.NoteDto;
import com.meretskiy.mytodover.exceptions_handling.AccessDeniedException;
import com.meretskiy.mytodover.exceptions_handling.ResourceNotFoundException;
import com.meretskiy.mytodover.model.Note;
import com.meretskiy.mytodover.model.User;
import com.meretskiy.mytodover.repositories.NoteRepository;
import com.meretskiy.mytodover.services.NoteService;
import com.meretskiy.mytodover.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultNoteService implements NoteService {

    private final UserService userService;

    private final NoteRepository noteRepository;

    @Override
    public List<Note> findAllNotesByOwnerName(String username) {
        return noteRepository.findAllByOwnerUsername(username);
    }

    @Override
    public Note createNote(String userName) {
        User user = userService.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Note note = new Note(user, "");
        noteRepository.save(note);
        return note;
    }

    @Override
    public Note findNote(String username, Long id) {
        return getNoteAndCheckAccess(username, id);
    }

    @Override
    public Note updateNote(String username, NoteDto noteDto) {
        Note note = getNoteAndCheckAccess(username, noteDto.getId());
        note.setArticle(noteDto.getArticle());
        note.setExecuted(noteDto.isExecuted());
        noteRepository.save(note);
        return note;
    }

    @Override
    public void deleteNote(String username, Long id) {
        getNoteAndCheckAccess(username, id);
        noteRepository.deleteById(id);
    }

    private Note getNoteAndCheckAccess(String username, Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        if (!note.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Requesting another user's data");
        }
        return note;
    }
}