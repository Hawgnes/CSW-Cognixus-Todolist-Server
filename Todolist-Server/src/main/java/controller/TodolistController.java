package controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1", produces="application/json")
//TODO: @CrossOrigin 
public class TodolistController {

	@PostMapping(path="/add", params="apiKey")
	public String addTodo() {
		
		return "";
	}
	
	@DeleteMapping(path="/del/{todoId}", params="apiKey")
	public void delTodoById() {
		
	}
	
	@GetMapping(path="/get", params="apiKey")
	public void getTodolist() {
		
	}
	
	@GetMapping(path="/get/{todoId}", params="apiKey")
	public void getTodoById(Long todoId) {
		
	}
	
}
