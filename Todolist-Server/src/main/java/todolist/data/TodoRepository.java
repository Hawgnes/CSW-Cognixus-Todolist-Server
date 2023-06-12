package todolist.data;

import org.springframework.data.repository.CrudRepository;

import todolist.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long>{
	// CrudRepository automatically generates the CRUD functionalities
	// it can also generate the implementations by the method signature
	public Iterable<Todo> findAllByOwnerId(String ownerId);
	
	public Todo findTodoByOwnerIdAndTodoId(String ownerId, Long todoId);
	
	public Iterable<Todo> findAllByOwnerIdAndTodoStatus(String ownerId, String todoStatus);
}
