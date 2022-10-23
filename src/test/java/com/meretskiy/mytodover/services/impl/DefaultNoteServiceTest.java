package com.meretskiy.mytodover.services.impl;

import com.meretskiy.mytodover.dto.NoteDto;
import com.meretskiy.mytodover.model.Note;
import com.meretskiy.mytodover.model.User;
import com.meretskiy.mytodover.repositories.NoteRepository;
import com.meretskiy.mytodover.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DefaultNoteServiceTest {

    public static final long ID = 1L;
    public static final String USER_NAME = "userName";

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private DefaultNoteService noteService;

    @Test
    public void findNoteTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        final Note mockNote = new Note();
        mockNote.setOwner(mockUser);
        mockNote.setCreatedAt(LocalDateTime.now());
        Mockito.when(noteRepository.findById(ID)).thenReturn(Optional.of(mockNote));

        final Note actualNote = noteService.findNote(mockUser.getUsername(), ID);

        Assertions.assertNotNull(actualNote);
        Assertions.assertEquals(mockNote, actualNote);
        Mockito.verify(noteRepository).findById(ID);
    }

    @Test
    public void findAllNotesByOwnerNameTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        final Note mockNote1 = new Note();
        mockNote1.setOwner(mockUser);
        mockNote1.setCreatedAt(LocalDateTime.now());
        final Note mockNote2 = new Note();
        mockNote2.setOwner(mockUser);
        mockNote2.setCreatedAt(LocalDateTime.now());
        List<Note> mockNoteList = new ArrayList<>();
        mockNoteList.add(mockNote1);
        mockNoteList.add(mockNote2);
        Mockito.when(noteRepository.findAllByOwnerUsername(USER_NAME)).thenReturn(mockNoteList);

        List<Note> allNotesByOwnerName = noteService.findAllNotesByOwnerName(USER_NAME);

        Assertions.assertNotNull(allNotesByOwnerName);
        Assertions.assertEquals(mockNoteList, allNotesByOwnerName);
        Mockito.verify(noteRepository).findAllByOwnerUsername(USER_NAME);
    }

    @Test
    public void createNoteTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        Mockito.when(userService.findByUsername(USER_NAME)).thenReturn(Optional.of(mockUser));

        Note actualNote = noteService.createNote(USER_NAME);

        Assertions.assertNotNull(actualNote);
        Assertions.assertEquals(mockUser, actualNote.getOwner());
        Mockito.verify(noteRepository).save(actualNote);
    }

    @Test
    public void updateNoteTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        final Note mockNote = new Note();
        mockNote.setId(ID);
        mockNote.setOwner(mockUser);
        mockNote.setCreatedAt(LocalDateTime.now());
        Mockito.when(noteRepository.findById(ID)).thenReturn(Optional.of(mockNote));

        Note actualNote = noteService.updateNote(USER_NAME, new NoteDto(mockNote));

        Assertions.assertNotNull(actualNote);
        Assertions.assertEquals(mockUser, actualNote.getOwner());
        Mockito.verify(noteRepository).save(actualNote);
    }

    @Test
    public void deleteNoteTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        final Note mockNote = new Note();
        mockNote.setId(ID);
        mockNote.setOwner(mockUser);
        mockNote.setCreatedAt(LocalDateTime.now());
        Mockito.when(noteRepository.findById(ID)).thenReturn(Optional.of(mockNote));

        noteService.deleteNote(USER_NAME, ID);

        Mockito.verify(noteRepository).deleteById(ID);
    }
}
