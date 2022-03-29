package com.example.notesservice.exception;

public class NoteIdNotFoundException extends RuntimeException{

	public NoteIdNotFoundException(String message) {
		super(message);
	}
}
