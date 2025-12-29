package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.model.Note;
import com.example.onlinelearningplatform.model.NoteComment;
import com.example.onlinelearningplatform.repository.NoteCommentRepository;
import com.example.onlinelearningplatform.repository.NoteRepository;
import com.example.onlinelearningplatform.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin
public class CommunityController {

    private final NoteRepository noteRepository;
    private final NoteCommentRepository noteCommentRepository;
    private final UserRepository userRepository;

    public CommunityController(NoteRepository noteRepository, NoteCommentRepository noteCommentRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.noteCommentRepository = noteCommentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/notes")
    public List<Note> notes(@RequestParam(defaultValue = "latest") String orderBy) {
        return orderBy.equals("hot") ? noteRepository.findByPublishedTrueOrderByLikesDesc() : noteRepository.findByPublishedTrueOrderByCreatedAtDesc();
    }

    @PostMapping("/notes")
    public Note createNote(@RequestBody Note note, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @PostMapping("/notes/{id}/like")
    public ResponseEntity<Note> like(@PathVariable Long id) {
        var note = noteRepository.findById(id).orElseThrow();
        note.setLikes(note.getLikes() + 1);
        return ResponseEntity.ok(noteRepository.save(note));
    }

    @PostMapping("/notes/{id}/comments")
    public ResponseEntity<NoteComment> comment(@PathVariable Long id, @RequestBody NoteComment comment, Authentication authentication) {
        var note = noteRepository.findById(id).orElseThrow();
        var user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        comment.setNote(note);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(noteCommentRepository.save(comment));
    }
}
