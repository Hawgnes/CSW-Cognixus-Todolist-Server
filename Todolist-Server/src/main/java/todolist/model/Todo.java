package todolist.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
public class Todo implements Persistable{
	@Id
	private Long todoId;
	
	private String todoTitle;
	private String todoDesc;
	private TODO_STATUS todoStatus = TODO_STATUS.NEW;
	
	private Long ownerId;
	private Date createdAt = new Date();
	
	public enum TODO_STATUS{
		NEW, IN_PROGRESS, COMPLETED
	}

	@Override
	public Object getId() {
		return this.todoId;
	}

	@Override
	public boolean isNew() {
		// true if no id = indicating this is a new entry
		return todoId == 0;
	}
}
