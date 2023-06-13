package todolist.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
@Table
public class Todo implements Persistable<Long>{
	@Id
	@Null(message="Todo ID is not allowed to be modified")
	private Long todoId;

	@NotBlank(message="Todo title must not be blank")
	@Length(min = 3, max = 255, message = "Todo title must be between 3 to 255 characters")
	private String todoTitle;
	
	@Length(max = 255, message = "Todo description must not be more than 255 characters")
	private String todoDesc;
	
	private TODO_STATUS todoStatus = TODO_STATUS.NEW;
	
	@Null(message="Owner ID is not allowed to be modified")
	// passed in with Principal
	private String ownerId;
	private Date createdAt = new Date();
	
	public enum TODO_STATUS{
		NEW, IN_PROGRESS, COMPLETED
	}

	@Override
	public Long getId() {
		return this.todoId;
	}

	@Override
	public boolean isNew() {
		// true if no id - indicating this is a new entry
		return todoId == null;
	}
	
}
