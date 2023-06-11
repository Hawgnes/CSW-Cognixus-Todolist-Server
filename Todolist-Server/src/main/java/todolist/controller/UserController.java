package todolist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.data.UserRepository;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private UserRepository userRepo;
	
	public UserController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
}
