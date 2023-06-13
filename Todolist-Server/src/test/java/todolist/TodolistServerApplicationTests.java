package todolist;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.controller.TodolistController;
import todolist.data.TodoRepository;
import todolist.model.Todo;

@WebMvcTest(TodolistController.class)
class TodolistServerApplicationTests {
	private final String END_POINT_PREFIX = "/api/v1";
	private final String END_POINT_ADD = END_POINT_PREFIX + "/add";
	private final String END_POINT_GET = END_POINT_PREFIX + "/get";
	private final String END_POINT_DELETE = END_POINT_PREFIX + "/delete";
	private final String END_POINT_UPDATE = END_POINT_PREFIX + "/update";
	
	// generate a valid bearer token then replace here for testing purpose
	private final String bearerToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg1YmE5MzEzZmQ3YTdkNGFmYTg0ODg0YWJjYzg0MDMwMDQzNjMxODAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI0MjQzOTk1NDAxMDYtcHQwZThmdm05dmo3ZTZmc2ljdmU3ZGMyNWJoY2d1MWMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI0MjQzOTk1NDAxMDYtcHQwZThmdm05dmo3ZTZmc2ljdmU3ZGMyNWJoY2d1MWMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTU0MDQ1NTEyOTc1NjAyNzQ0NTgiLCJlbWFpbCI6InNlbmd3YWg5Ny5kZXYyQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiRk5SZ1lXcXJaM0h4aTJZVnlhSk1xdyIsIm5hbWUiOiJTZW5nIFdhaCIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQWNIVHRjRTJBUENSUFpXMzE1NFpfa1UtdnlxS01sYUl2S2hqN08yTUNUaz1zOTYtYyIsImdpdmVuX25hbWUiOiJTZW5nIFdhaCIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjg2NjQyNTY0LCJleHAiOjE2ODY2NDYxNjR9.e5sY9AB4adb-Bi8-00vsd66t81yFNaAI5LD7dVkJBY-0SeyFpqQsoRHeN84Ca_vlsHfINc35Y5EThSVAKKi0R0LAAdz-ZgZzU4WslBr7LzIpQL7I-1O2KyWZPjiHo0nPwZeqpItu-nntaGrNRfDn1-gBTdG-WC0krFGKSc7AugN55tGyMjYbg7XqdqWsJjX2_dK73uKwuzsF1EHUhIBIACKNVG311MQBcOYCYAyabs-FbHxtH706KnV6tIoTNaJtHPEFCMxesomF_7C-5VrphsTGUbk95WV0mMa-gJqp9q8dUH831P5Yog_or5ORlAKdbrEsjO90O1MZ9g9yJTZgZQ";
	
	// used to perform API call
	@Autowired private MockMvc mockMvc;
	// serialize object to JSON string
	@Autowired private ObjectMapper objectMapper;
	// mock database 
	@MockBean private TodoRepository todoRepo;
	// mock principal for user identification
	Principal mockPrincipal = Mockito.mock(Principal.class);
	
	@Test
	public void testAddShouldReturn400BadRequest() throws Exception {
		Todo newTodo = new Todo();
		newTodo.setTodoTitle("");
		newTodo.setTodoDesc(
				"asdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddada" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad"); 
		
		String requestBody = objectMapper.writeValueAsString(newTodo);
		
		mockMvc
			.perform(
				post(END_POINT_ADD).header("Authorization", "Bearer " + bearerToken)
				.contentType("application/json").content(requestBody)
			)
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
	
	@Test
	public void testAddShouldReturn201Created() throws Exception {		
		Todo newTodo = new Todo();
		newTodo.setTodoTitle("Sample Todo Title");
		newTodo.setTodoDesc("Sample Todo description");
		
		Mockito.when(todoRepo.save(newTodo)).thenReturn(newTodo);
		
		String requestBody = objectMapper.writeValueAsString(newTodo);
		
		mockMvc
			.perform(
				post(END_POINT_ADD).header("Authorization", "Bearer " + bearerToken)
				.contentType("application/json").content(requestBody)
			)
			.andExpect(status().isCreated())
			.andDo(print());
	}
	
	@Test
	public void testGetShouldReturn404NotFound() throws Exception {
		Long mockTodoId = 999999L;
		String END_POINT_GET_SPECIFIC = END_POINT_GET + "/" + mockTodoId;
				
		mockMvc
			.perform(get(END_POINT_GET_SPECIFIC).header("Authorization", "Bearer " + bearerToken))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	public void testGetShouldReturn400BadRequest() throws Exception {
		String END_POINT_GET_SPECIFIC = END_POINT_GET + "/" + "a";
		
		mockMvc
			.perform(get(END_POINT_GET_SPECIFIC).header("Authorization", "Bearer " + bearerToken))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
	
	@Test
	public void testGetShouldReturn200OK() throws Exception {
		Long mockTodoId = 1L;
		String END_POINT_GET_SPECIFIC = END_POINT_GET + "/" + mockTodoId;
		
		Todo todo = new Todo();
		todo.setTodoId(mockTodoId);
		todo.setTodoTitle("Sample todo title");
		todo.setOwnerId(mockPrincipal.getName());
		todo.setTodoStatus(Todo.TODO_STATUS.NEW);
		todo.setCreatedAt(new Date());
		
		Mockito.when(todoRepo.findTodoByOwnerIdAndTodoId(anyString(), anyLong())).thenReturn(todo);
		
		mockMvc
			.perform(get(END_POINT_GET_SPECIFIC).header("Authorization", "Bearer " + bearerToken).contentType("application/json"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.todoId", is(1)))
			.andDo(print());
	}
	
	@Test
	public void testGetAllShouldReturn200OK() throws Exception{
		
		Todo todo1 = new Todo();
		todo1.setTodoId(1L);
		todo1.setTodoTitle("Sample todo title");
		todo1.setOwnerId(mockPrincipal.getName());
		todo1.setTodoStatus(Todo.TODO_STATUS.NEW);
		todo1.setCreatedAt(new Date());

		Todo todo2 = new Todo();
		todo2.setTodoId(2L);
		todo2.setTodoTitle("Sample todo title");
		todo2.setOwnerId(mockPrincipal.getName());
		todo2.setTodoStatus(Todo.TODO_STATUS.NEW);
		todo2.setCreatedAt(new Date());
		
		Iterable<Todo> todos = List.of(todo1, todo2);
		
		Mockito.when(todoRepo.findAllByOwnerId(anyString())).thenReturn(todos);

		mockMvc
			.perform(get(END_POINT_GET).header("Authorization", "Bearer " + bearerToken).contentType("application/json"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].todoId", is(1)))
			.andExpect(jsonPath("$[1].todoId", is(2)))
			.andDo(print());
	}

	@Test
	public void testUpdateShouldReturn404NotFound() throws Exception {
		Long mockTodoId = 999999L;
		String END_POINT_UPDATE_SPECIFIC = END_POINT_UPDATE + "/" + mockTodoId;
		
		Todo todo = new Todo();
		
		Mockito.when(todoRepo.findTodoByOwnerIdAndTodoId(mockPrincipal.getName(), 3L)).thenReturn(todo);
		
		Todo patch = new Todo();
		patch.setTodoTitle("Sample patch Title");
		patch.setTodoDesc("Sample patch description");
				
		String requestBody = objectMapper.writeValueAsString(patch);
		
		mockMvc
			.perform(
				patch(END_POINT_UPDATE_SPECIFIC).header("Authorization", "Bearer " + bearerToken)
				.contentType("application/json").content(requestBody))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	public void testUpdateShouldReturn400BadRequest() throws Exception {
		Long mockTodoId = 999999L;
		String END_POINT_UPDATE_SPECIFIC = END_POINT_UPDATE + "/" + mockTodoId;
		
		Todo todo = new Todo();
		
		Mockito.when(todoRepo.findTodoByOwnerIdAndTodoId(anyString(), anyLong())).thenReturn(todo);
		
		Todo patch = new Todo();
		patch.setTodoTitle("12");
		patch.setTodoDesc(
				"asdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddada" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad" + 
				"dasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddadadasdasdasdsdasddad");
				
		String requestBody = objectMapper.writeValueAsString(patch);
		
		mockMvc
			.perform(
				patch(END_POINT_UPDATE_SPECIFIC).header("Authorization", "Bearer " + bearerToken)
				.contentType("application/json").content(requestBody))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
	
	@Test
	public void testUpdateShouldReturn200OK() throws Exception {
		Long mockTodoId = 999999L;
		String END_POINT_UPDATE_SPECIFIC = END_POINT_UPDATE + "/" + mockTodoId;
		
		Todo todo = new Todo();
		
		Mockito.when(todoRepo.findTodoByOwnerIdAndTodoId(anyString(), anyLong())).thenReturn(todo);
		
		Todo patch = new Todo();
		patch.setTodoTitle("Sample patch Title");
		patch.setTodoDesc("Sample patch description");
				
		String requestBody = objectMapper.writeValueAsString(patch);
		
		mockMvc
			.perform(
				patch(END_POINT_UPDATE_SPECIFIC).header("Authorization", "Bearer " + bearerToken)
				.contentType("application/json").content(requestBody))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void testDeleteShouldReturn404NotFound() throws Exception {
		Long mockTodoId = 999999L;
		String END_POINT_DELETE_SPECIFIC = END_POINT_DELETE + "/" + mockTodoId;
		
		Mockito.doThrow(IllegalArgumentException.class).when(todoRepo).delete(null);
		
		mockMvc
		.perform(
			delete(END_POINT_DELETE_SPECIFIC).header("Authorization", "Bearer " + bearerToken)
			.contentType("application/json"))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	
	@Test
	public void testDeleteShouldReturn204NoContent() throws Exception {
		Long mockTodoId = 999999L;
		String END_POINT_DELETE_SPECIFIC = END_POINT_DELETE + "/" + mockTodoId;
		
		Todo todo = new Todo();
		
		Mockito.doNothing().when(todoRepo).delete(todo);
		
		mockMvc
		.perform(
			delete(END_POINT_DELETE_SPECIFIC).header("Authorization", "Bearer " + bearerToken)
			.contentType("application/json"))
		.andExpect(status().isNoContent())
		.andDo(print());
	}
}
