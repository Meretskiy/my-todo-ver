package com.meretskiy.mytodover.repositories;

import com.meretskiy.mytodover.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByOwnerUsername(String ownerUsername);
}
