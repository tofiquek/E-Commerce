package com.bacancy.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
		
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDto>> findAllUsers(){
		List<UserDto> users = userService.allUsers();
		return new ResponseEntity(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUser(@PathVariable(name = "id") Long id) {
		return new ResponseEntity( userService.getUserById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
		return new ResponseEntity(userService.addUser(userDto), HttpStatus.OK) ;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@PathVariable(name="id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity(HttpStatus.OK); 
	}
}
