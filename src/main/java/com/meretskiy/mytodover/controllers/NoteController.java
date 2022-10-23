package com.meretskiy.mytodover.controllers;

import com.meretskiy.mytodover.dto.NoteDto;
import com.meretskiy.mytodover.exceptions_handling.AccessDeniedException;
import com.meretskiy.mytodover.exceptions_handling.ResourceNotFoundException;
import com.meretskiy.mytodover.exceptions_handling.TodoverError;
import com.meretskiy.mytodover.model.Note;
import com.meretskiy.mytodover.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<?> getCurrentUserNotes(Principal principal) {
        return ResponseEntity.ok(noteService.findAllNotesByOwnerName(principal.getName()).stream()
                .map(NoteDto::new)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<?> createNote(Principal principal) {
        try {
            Note note = noteService.createNote(principal.getName());
            return new ResponseEntity<>(new NoteDto(note), HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.BAD_REQUEST.value(),
                    "User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(Principal principal, @PathVariable Long id) {
        try {
            Note note = noteService.findNote(principal.getName(), id);
            return ResponseEntity.ok(new NoteDto(note));
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.FORBIDDEN.value(),
                    "Requesting another user's data"), HttpStatus.FORBIDDEN);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.BAD_REQUEST.value(),
                    "Note not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateNote(Principal principal, @RequestBody NoteDto noteDto) {
        try {
            Note note = noteService.updateNote(principal.getName(), noteDto);
            return ResponseEntity.ok(new NoteDto(note));
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.FORBIDDEN.value(),
                    "Requesting another user's data"), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNoteById(Principal principal, @PathVariable Long id) {
        try {
            noteService.deleteNote(principal.getName(), id);
            return ResponseEntity.ok().build();
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.FORBIDDEN.value(),
                    "Requesting another user's data"), HttpStatus.FORBIDDEN);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.BAD_REQUEST.value(),
                    "Note not found"), HttpStatus.BAD_REQUEST);
        }
    }
}
