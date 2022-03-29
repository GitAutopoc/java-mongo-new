package com.example.notesservice.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.notesservice.document.Note;

public interface NoteRepository extends MongoRepository<Note, String>{

}
