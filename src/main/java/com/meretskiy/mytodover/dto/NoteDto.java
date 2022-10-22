package com.meretskiy.mytodover.dto;

import com.meretskiy.mytodover.model.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NoteDto {

    private Long id;
    private String username;
    private String article;
    private boolean isExecuted;
    private String creationDateTime;

    public NoteDto(Note note) {
        this.id = note.getId();
        this.username = note.getOwner().getUsername();
        this.article = note.getArticle();
        this.isExecuted = note.isExecuted();
        this.creationDateTime = note.getCreatedAt().toString();
    }
}
