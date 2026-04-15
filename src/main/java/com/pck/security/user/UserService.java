package com.pck.security.user;

import com.pck.security.SecurityApplication;

import java.beans.Transient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;


    
	@Transactional
	public ResponseEntity<User> addUser(UserRequest userRequest){
		
		User savedUser=userRepository.findByUserName(userRequest.getUserName());
		
		if(savedUser ==null) {
			savedUser=new User();
		}
				
		savedUser.setUserName(userRequest.getUserName());
		savedUser.setPassword(userRequest.getPassword());
		savedUser.setName(userRequest.getName());
		
		try {
			userRepository.save(savedUser);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@Transactional
	public ResponseEntity<List<User>> getUsers(){
		List<User> users=userRepository.findAll();
		if(users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<User> getUser(Integer userId){
		User user=userRepository.findUserById(userId);
		if(user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<User> deleteUser(Integer userId){
		User user=userRepository.findUserById(userId);
		
		try {
			userRepository.delete(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	

}
