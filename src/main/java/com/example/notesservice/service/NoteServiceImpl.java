package com.example.notesservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.notesservice.dto.NotesDto;
import com.example.notesservice.repo.NoteRepository;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteRepository noteRepository;

	@Override
	public List<NotesDto> findAll() {
		return null;
	}

	@Override
	public NotesDto findById(String id) {
		return null;
	}
	@Override
	public NotesDto addNote(NotesDto noteDto) {
		return null;
	}

	@Override
	public NotesDto deleteNote(String id) {
		return null;
	}

	@Override
	public List<NotesDto> findAllByStatus(String status) {
		return null;
	}

	@Override
	public List<NotesDto> findAllByAuthor(String author) {
		return null;
	}

	@Override
	public NotesDto updateStatus(String id, String status) {
		return null;
	}
}
