package com.meretskiy.mytodover.services;

import com.meretskiy.mytodover.dto.NoteDto;
import com.meretskiy.mytodover.exceptions_handling.AccessDeniedException;
import com.meretskiy.mytodover.exceptions_handling.ResourceNotFoundException;
import com.meretskiy.mytodover.model.Note;
import com.meretskiy.mytodover.model.User;
import com.meretskiy.mytodover.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final UserService userService;
    private final NoteRepository noteRepository;

    public List<NoteDto> findAllNotesByOwnerName(String username) {
        return noteRepository.findAllByOwnerUsername(username).stream()
                .map(NoteDto::new)
                .collect(Collectors.toList());
    }

    public NoteDto createNote(String userName, String article) {
        User user = userService.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Note note = new Note(user, article);
        noteRepository.save(note);
        return new NoteDto(note);
    }

    public NoteDto findNoteById(String username, Long id) {
        return new NoteDto(getNoteAndCheckAccess(username, id));
    }

    public NoteDto updateNote(String username, NoteDto noteDto) {
        Note note = getNoteAndCheckAccess(username, noteDto.getId());
        note.setArticle(noteDto.getArticle());
        note.setExecuted(noteDto.isExecuted());
        noteRepository.save(note);
        return new NoteDto(note);
    }

    public void deleteNoteById(String username, Long id) {
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