package com.example.notesservice.exception;
import static com.example.utils.TestUtils.currentTest;
import static com.example.utils.TestUtils.exceptionTestFile;
import static com.example.utils.TestUtils.yakshaAssert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.notesservice.controller.NoteController;
import com.example.notesservice.dto.NotesDto;
import com.example.notesservice.dto.NotesExceptionResponse;
import com.example.notesservice.service.NoteService;

@Order(2)
@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc
public class TestExceptions {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NoteService noteService;

	
	
	
		// Exception
		@Test
		public void testDataValidationCheckIsAddedInController() throws Exception {
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			notesdto.setAuthor("pr");
			Mockito.when(noteService.addNote(notesdto))
			.thenReturn(notesdto);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
					.content(com.example.utils.MasterData.asJsonString(notesdto))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
					
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
			System.out.println(result.getResponse().getStatus());
			yakshaAssert(currentTest(),
					result.getResponse().getStatus() == 400? true : false,
							exceptionTestFile);
					
		}
		
		@Test
		public void testAbleToWorkWithCustomExceptions() throws Exception {
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			notesdto.setAuthor("pr");
			Mockito.when(noteService.addNote(notesdto))
			.thenReturn(notesdto);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
					.content(com.example.utils.MasterData.asJsonString(notesdto))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
					
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
			
			yakshaAssert(currentTest(),
					result.getResponse().getStatus() == 400? true : false,
							exceptionTestFile);
					
		}
		
		@Test
		public void testExceptionIsThrownAndHandledInCaseOfInvalidData() throws Exception {
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			notesdto.setAuthor("pr");
			Mockito.when(noteService.addNote(notesdto))
			.thenReturn(notesdto);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
					.content(com.example.utils.MasterData.asJsonString(notesdto))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
					
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
			
			yakshaAssert(currentTest(),
					result.getResponse().getStatus() == 400? true : false,
							exceptionTestFile);
					
		}
		
		@Test
		public void testAbleToImplementVariousResponseCodeWithCustomizedMessage() throws Exception {
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			notesdto.setAuthor("pr");
			Mockito.when(noteService.addNote(notesdto))
			.thenReturn(notesdto);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
					.content(com.example.utils.MasterData.asJsonString(notesdto))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
					
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
			
			yakshaAssert(currentTest(),
					result.getResponse().getStatus() == 400? true : false,
							exceptionTestFile);
					
		}
		
		@Test
		void testExceptionIsThrownAndHandledIfNoteIdIsNotValidWhileDeleting() throws Exception{
			
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			String id = notesdto.getId();
			
			NotesExceptionResponse exResponse = new NotesExceptionResponse("Note with Id - " + id + " not found!", System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
			Mockito.when(noteService.deleteNote(id))
			.thenThrow(new NoteIdNotFoundException("Note with Id - " + id + " not found!"));
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/noteservice/delete/" + id)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
					
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			yakshaAssert(currentTest(),
					result.getResponse().getContentAsString().contains(exResponse.getMessage())? true : false,exceptionTestFile);
			
		}
	
		@Test
		void testExceptionIsThrownAndHandledIfNoteIdIsNotValidWhileGettingNoteById() throws Exception{
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			String id = notesdto.getId();
			
			NotesExceptionResponse exResponse = new NotesExceptionResponse("Note with Id - " + id + " not found!", System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
			Mockito.when(noteService.findById(id))
			.thenThrow(new NoteIdNotFoundException("Note with Id - " + id + " not found!"));
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/noteservice/get/" + id)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
					
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			yakshaAssert(currentTest(),
					(result.getResponse().getContentAsString().contains(exResponse.getMessage())? true : false),exceptionTestFile);
			
		}
		
		@Test
		public void testExceptionIsThrownAndHandledIfNoteIdIsNotValidWhileUpdating() throws Exception{
			NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
			String id = "10001";
			String status="pending";
			notesdto.setId(id);
			NotesExceptionResponse exResponse = new NotesExceptionResponse("Note with Id - " + id + " not found!", System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
			notesdto.setStatus(status);
			Mockito.when(noteService.updateStatus(id, status)).thenThrow(new NoteIdNotFoundException("Note with Id - " + id + " not found!"));
			RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/noteservice/update/10001/pending")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON);
			
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
			
			yakshaAssert(currentTest(), 
					(result.getResponse().getContentAsString().contains(exResponse.getMessage())? "true" : "false"),	exceptionTestFile);
		}
	
}
