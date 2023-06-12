package todolist.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import todolist.data.UserRepository;
import todolist.model.User;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	// implicitly autowired by Spring
	public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping(path="/register", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public User registerUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	//TODO: authenticate first, have user be flagged as logged-in in server?
	@PostMapping("/{userId}/reset-api")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String resetApiKey(@PathVariable Long userId) {
		User user = userRepo.findById(userId).get();
		String newUUID = UUID.randomUUID().toString();
		user.setApiKey(newUUID);
		userRepo.save(user);
		return newUUID;
	}
	
	@PatchMapping("/{userId}/update")
	public User patchUser(@PathVariable Long userId, @RequestBody User patch) {
		User user = userRepo.findById(userId).get();
		
		if (patch.getEmail() != null) {
			user.setEmail(patch.getEmail());
		}
		if (patch.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(patch.getPassword()));
		}
		
		return userRepo.save(user);
	}
}
