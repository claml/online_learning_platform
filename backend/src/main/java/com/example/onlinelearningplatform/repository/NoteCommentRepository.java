package com.example.onlinelearningplatform.repository;

import com.example.onlinelearningplatform.model.Note;
import com.example.onlinelearningplatform.model.NoteComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteCommentRepository extends JpaRepository<NoteComment, Long> {
    List<NoteComment> findByNote(Note note);
}
