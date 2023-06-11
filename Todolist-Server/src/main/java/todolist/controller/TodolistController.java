package todolist.controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import todolist.data.TodoRepository;
import todolist.model.Todo;

@RestController
@RequestMapping(path="/api/v1", produces="application/json")
//@CrossOrigin(origins="http://todolist:8080")
public class TodolistController {
	private TodoRepository todoRepo;
	
	public TodolistController(TodoRepository todoRepo) {
		this.todoRepo = todoRepo;
	}

	@PostMapping(path="/add", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Todo addTodo(@RequestBody Todo todo) {
		// TODO: get and set ownerID 
		return todoRepo.save(todo);
	}
	
	@DeleteMapping("/del/{todoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delTodoById(@PathVariable("todoId") Long todoId) {		
		try {
			todoRepo.deleteById(todoId);
		} 
		catch (EmptyResultDataAccessException e) { 
		// TODO: a response entity with error code and error message
			
		}
		// TODO: a response entity with success message
		
	}
	
	@GetMapping("/get/{todoId}")
	public ResponseEntity<Todo> getTodoById(@PathVariable("todoId") Long todoId) {
		Optional<Todo> selectedTodo = todoRepo.findById(todoId);
		if (selectedTodo.isPresent()) {
			return new ResponseEntity<>(selectedTodo.get(), HttpStatus.OK);
		}
		// TODO: a response entity with error code and error message
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/get")
	public Iterable<Todo> getTodolist() {
		return todoRepo.findAll();
	}
	
	@PatchMapping(path="/update/{todoId}", consumes="application/json")
	public Todo patchTodo(@PathVariable("todoId") Long todoId, @RequestBody Todo patch) {
		Todo todo = todoRepo.findById(todoId).get();
		
		// TODO: test invalid data patching
		if (patch.getTodoTitle() != null) {
			todo.setTodoTitle(patch.getTodoTitle());
		}
		if (patch.getTodoDesc() != null) {
			todo.setTodoDesc(patch.getTodoDesc());
		}
		try {
			if (patch.getTodoStatus() != null) {
				todo.setTodoStatus(patch.getTodoStatus());
			}
		}
		catch (HttpMessageNotReadableException e) { }
		
		
		return todoRepo.save(todo);
	}
	
}
