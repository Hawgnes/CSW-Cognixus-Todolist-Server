package todolist.data;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import todolist.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	// CrudRepository automatically generates the CRUD functionalities
}
