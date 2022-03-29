package com.example.notesservice.boundary;
import static com.example.utils.TestUtils.boundaryTestFile;

import static com.example.utils.TestUtils.currentTest;
import static com.example.utils.TestUtils.yakshaAssert;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.Order;

import com.example.notesservice.dto.NotesDto;
import com.example.utils.MasterData;

@Order(3)
public class BoundaryTests {
	
	
	private static Validator validator;
	
	

    //----------------------------------------------------------------------------------------------
    @BeforeAll
    public static void setUp() {
    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
    @Test
	public void testHibernateValidationIsAddedToCheckIfAuthorNameIsNotNull() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setAuthor("");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfAuthorNameIsNotLessThanFiveChars() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setAuthor("Pra");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfAuthorNameIsNotMoreThanTwentyChars() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		String name = "";
		for(int i=0;i<25;i++) {
			name.concat("A");
		}
		note.setAuthor(name);
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
	}
	 
	@Test
	public void testHibernateValidationIsAddedToCheckIfTitleIsNotNull() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setTitle("");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfTitleIsNotLessThanFiveChars() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setTitle("Dev");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfTitleIsNotMoreThanTwentyChars() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		String name = "";
		for(int i=0;i<25;i++) {
			name.concat("A");
		}
		note.setTitle(name);
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfDescriptionIsNotNull() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setDescription("");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfDescriptionIsNotLessThanFiveChars() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setDescription("Test");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfDescriptionIsNotMoreThanTwoHundredChars() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		String name = "";
		for(int i=0;i<225;i++) {
			name.concat("A");
		}
		note.setDescription(name);
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
	
	@Test
	public void testHibernateValidationIsAddedToCheckIfStatusIsNotNull() throws Exception {
		NotesDto note = MasterData.getNotesDto();
		note.setStatus("");
		Set<ConstraintViolation<NotesDto>> violations = validator.validate(note);
		yakshaAssert(currentTest(),!violations.isEmpty()? true : false,boundaryTestFile);
			
	}
}
