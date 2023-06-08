package data;

import org.springframework.data.repository.CrudRepository;

import model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long>{

}
