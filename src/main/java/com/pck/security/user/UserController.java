package com.pck.security.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pck.security.userutil.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/get-all")
	public ResponseEntity<List<User>> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/get-user/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Integer userId) {
		return userService.getUser(userId);
	}

	@PostMapping("/addUser")
	public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest) {
		userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		return userService.addUser(userRequest);
	}

	@GetMapping("/delete-user/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
		return userService.deleteUser(userId);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequest loginRequest) {
		
		User user = userRepository.findByUserName(loginRequest.getUserName());

		if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			String token = jwtUtils.generateToken(user.getUserName());
			return ResponseEntity.ok(Map.of("token", token));
		} else {
			return ResponseEntity.status(401).body("Invalid username or password");
		}
	}
}