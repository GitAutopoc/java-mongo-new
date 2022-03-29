package com.example.notesservice.exception;

public class InvalidNoteDataException extends RuntimeException{

	public InvalidNoteDataException(String message) {
		super(message);
	}
}
