package todolist.controller;

import java.security.Principal;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import todolist.data.TodoRepository;
import todolist.exception.ResponseEntityBuilder;
import todolist.model.Todo;

@Slf4j
@RestController
@RequestMapping(path="/api/v1", produces="application/json")
public class TodolistController {
	private TodoRepository todoRepo;
	
	// Spring implicitly applies autowiring of dependencies through constructor's parameter
	// when there's only one constructor
	// use @Autowired to explicitly state autowiring, or if there's more than one constructor	
	public TodolistController(TodoRepository todoRepo) {
		this.todoRepo = todoRepo;
	}

	@PostMapping(path="/add")
	@ResponseStatus(HttpStatus.CREATED)
	public Todo addTodo(@Valid @RequestBody Todo todo, Principal principal) {
		todo.setOwnerId(principal.getName());
		return todoRepo.save(todo);
	}
	
	/* if Content-Type is using x-www-form-urlencoded
	 * sample curl: curl -X POST -H "Authorization: Bearer %bearerToken%" -d 
	 * "todoTitle=Sample Y Todo&todoDesc=Sample desc" http://localhost:8080/api/v1/add
	 * 
	@PostMapping(path="/add", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public Todo addTodoXWWWFormUrlEncoded(@RequestParam MultiValueMap<String, String> paramMap, Principal principal) {
		log.info(paramMap.toString());
		Todo todo = new Todo();
		todo.setTodoTitle(paramMap.get("todoTitle").get(0));
		todo.setTodoDesc(paramMap.get("todoDesc").get(0));
		todo.setOwnerId(principal.getName());
		return todoRepo.save(todo);
	}
	*/
	
	// attempting to pass invalid value to {todoId} will be caught in CustomExceptionHandler.java
	
	@DeleteMapping("/delete/{todoId}")
	public ResponseEntity<Object> delTodoById(@PathVariable("todoId") Long todoId, Principal principal) {		
		try {
			todoRepo.delete(todoRepo.findTodoByOwnerIdAndTodoId(principal.getName(), todoId));
		} 
		catch (IllegalArgumentException e) {
			return ResponseEntityBuilder.responseBuilder("You do not have a Todo with this ID", HttpStatus.NOT_FOUND);
		}
		return ResponseEntityBuilder.responseBuilder("Successfully deleted Todo " + todoId, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/get/{todoId}")
	public ResponseEntity<Object> getTodoById(
			@PathVariable("todoId") Long todoId, Principal principal) {
		Todo todo = todoRepo.findTodoByOwnerIdAndTodoId(principal.getName(), todoId);
		if (todo != null) {
			return new ResponseEntity<>(todo, HttpStatus.OK);
		}
		else {
			return ResponseEntityBuilder.responseBuilder("You do not have a Todo with this ID", HttpStatus.NOT_FOUND);
		}
    }
	
	@GetMapping("/get")
	public Iterable<Todo> getTodolist(
			@RequestParam(name="status", required=false) Todo.TODO_STATUS todoStatus, 
			Principal principal) {
		if (todoStatus != null) {
			// filter by todoStatus
			// invalid value will be handled by CustomExceptionHandler.java
			return todoRepo.findAllByOwnerIdAndTodoStatus(principal.getName(), todoStatus.name());
		}
		else {
			return todoRepo.findAllByOwnerId(principal.getName());
		}
	}
	
	@PatchMapping(path="/update/{todoId}")
	public ResponseEntity<Object> patchTodo(
			@PathVariable("todoId") Long todoId, 
			@Valid @RequestBody Todo patch, 
			Principal principal) {
		Todo todo = todoRepo.findTodoByOwnerIdAndTodoId(principal.getName(), todoId);
		
		if (todo != null) {
			if (patch.getTodoTitle() != null) {
				todo.setTodoTitle(patch.getTodoTitle());
			}
			if (patch.getTodoDesc() != null) {
				todo.setTodoDesc(patch.getTodoDesc());
			}
			if (patch.getTodoStatus() != null) {
				todo.setTodoStatus(patch.getTodoStatus());
			}
			return new ResponseEntity<>(todoRepo.save(todo), HttpStatus.OK);
		}
		else {
			return ResponseEntityBuilder.responseBuilder("You do not have a Todo with this ID", HttpStatus.NOT_FOUND);
		}

	}
	


	
}
