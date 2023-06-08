package data;

import org.springframework.data.repository.CrudRepository;

import model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	// CrudRepository automatically generates the CRUD functionalities
}
