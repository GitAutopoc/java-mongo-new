package com.example.notesservice.functional;

import static com.example.utils.TestUtils.asJsonString;
import static com.example.utils.TestUtils.businessTestFile;
import static com.example.utils.TestUtils.currentTest;
import static com.example.utils.TestUtils.testReport;
import static com.example.utils.TestUtils.yakshaAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;

import com.example.notesservice.document.Note;
import com.example.notesservice.dto.NotesDto;
import com.example.notesservice.repo.NoteRepository;
import com.example.notesservice.service.NoteService;
import com.example.notesservice.service.NoteServiceImpl;
import com.example.utils.MasterData;

@Order(4)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SkillTest {

	
	@Mock
	private NoteRepository repository;
	
	@Autowired
	@InjectMocks
	private NoteServiceImpl noteService;
	
	/*@BeforeEach
	public void setUp() {
		this.noteService = new NoteServiceImpl();
	}*/
	
	@AfterAll
	public static void afterAll() {
		testReport();
	}
	
	
	@Test
	public void testStreamsAreUsedToIterateCollections() throws Exception {
		final int count[] = new int[1];
		
		List<Note> notes = new ArrayList<Note>();
		Note note1 = MasterData.getNotes();
		notes.add(note1);
		Note note2 = MasterData.getNotes();
		note2.setId("2");
		notes.add(note1);
		
		
		/*List<NotesDto> noteDtos = new ArrayList<NotesDto>();
		NotesDto noteDto1 = MasterData.getNotesDto();
		noteDtos.add(noteDto1);
		NotesDto noteDto2 = MasterData.getNotesDto();
		noteDto2.setId(2);
		noteDtos.add(noteDto1);*/
		List<Note> list = Mockito.mock(List.class);
		Mockito.when(this.repository.findAll()).thenReturn(list);
		
		
		Mockito.when(list.stream()).then(new Answer<Stream<Note>>() {

			@Override
			public Stream<Note> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return notes.stream();
			}
			
		});
		try {
			List<NotesDto> noteDtos  = this.noteService.findAll();
		}catch(Exception ex) {
			System.out.println(ex);
			yakshaAssert(currentTest(), 
					false,	
					businessTestFile);
			return;

		}
		yakshaAssert(currentTest(), 
				count[0] == 1? true : false,	
				businessTestFile);
	}
	
	@Test
	public void testAbleToUseLambdas() throws Exception {
		final int count[] = new int[1];
		
		List<Note> notes = new ArrayList<Note>();
		Note note1 = MasterData.getNotes();
		notes.add(note1);
		Note note2 = MasterData.getNotes();
		note2.setId("2");
		notes.add(note1);
		
		
		/*List<NotesDto> noteDtos = new ArrayList<NotesDto>();
		NotesDto noteDto1 = MasterData.getNotesDto();
		noteDtos.add(noteDto1);
		NotesDto noteDto2 = MasterData.getNotesDto();
		noteDto2.setId(2);
		noteDtos.add(noteDto1);*/
		List<Note> list = Mockito.mock(List.class);
		Mockito.when(this.repository.findAll()).thenReturn(list);
		
		
		Mockito.when(list.stream()).then(new Answer<Stream<Note>>() {

			@Override
			public Stream<Note> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return notes.stream();
			}
			
		});
		try {
			List<NotesDto> noteDtos  = this.noteService.findAll();
		}catch(Exception ex) {
			System.out.println(ex);
			yakshaAssert(currentTest(), 
					false,	
					businessTestFile);
			return;

		}
		yakshaAssert(currentTest(), 
				count[0] == 1? true : false,	
				businessTestFile);
	}
}
