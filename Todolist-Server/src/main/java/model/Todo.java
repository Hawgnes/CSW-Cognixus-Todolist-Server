package model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Todo {
	@Id
	private Long todoId;
	
	private String todoTitle;
	private String todoDesc;
	private TODO_STATUS todoStatus;
	
	private enum TODO_STATUS{
		NEW, IN_PROGRESS, COMPLETED, DELETED
	}
}
