package model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {
	@Id
	private Long userId;
	
	private String email;
	private String password;
	private String apiKey;
	
	private List<Todo> todoList;
}
