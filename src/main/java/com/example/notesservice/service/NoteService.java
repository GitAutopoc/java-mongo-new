package com.example.notesservice.service;

import java.util.List;
import java.util.Optional;

import com.example.notesservice.document.Note;
import com.example.notesservice.dto.NotesDto;

public interface NoteService {
	public List<NotesDto> findAll();
	public NotesDto findById(String id);
	public NotesDto addNote(NotesDto note);
	public NotesDto deleteNote(String id);
	public List<NotesDto> findAllByStatus(String status);
	public List<NotesDto> findAllByAuthor(String author);
	public NotesDto updateStatus(String id, String status);
}
