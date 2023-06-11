package todolist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class User {
	@Id
	private Long userId;
	
	private String email;
	private String password;
	private String apiKey;
	
	private Date createdAt;

	private List<Todo> todoList = new ArrayList<>();
	
	void createdAt() {
		this.createdAt = new Date();
	}
	
	public void addTodo(Todo todo) {
		this.todoList.add(todo);
	}
}
