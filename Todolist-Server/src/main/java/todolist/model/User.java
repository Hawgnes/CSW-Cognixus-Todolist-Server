package todolist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class User implements Persistable<Long>{
	@Id
	private Long userId;
	
	private String email;
	private String password;
	private String apiKey = UUID.randomUUID().toString();
	
	private Date createdAt = new Date();

	/*
	private List<Todo> todoList = new ArrayList<>();
	
	public void addTodo(Todo todo) {
		this.todoList.add(todo);
	}
	 */
	@Override
	public Long getId() {
		return this.userId;
	}

	@Override
	public boolean isNew() {
		// mark as true if no userId
		return this.userId == null;
	}
}
