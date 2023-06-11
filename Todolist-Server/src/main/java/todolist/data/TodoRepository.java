package todolist.data;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import todolist.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long>{
	// CrudRepository automatically generates the CRUD functionalities
}
