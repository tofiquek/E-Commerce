package com.bacancy.ecommerce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.service.UserService;
import com.bacancy.ecommerce.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping
	public ResponseEntity<List<UserDto>> findAllUsers(){
		logger.info("findAllUser method started");
		logger.info("URI = "+ request.getRequestURI());
		List<UserDto> users = userService.allUsers();
		logger.info("findAllUser method Ended");
		return new ResponseEntity(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUser(@PathVariable(name = "id") Long id) {
		logger.info("findUser method started");
		logger.info("URI = "+ request.getRequestURI());
		return new ResponseEntity( userService.getUserById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
		logger.info("saveUser method started");
		logger.info("URI = "+ request.getRequestURI());
		return new ResponseEntity(userService.addUser(userDto), HttpStatus.OK) ;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@PathVariable(name="id") Long id) {
		logger.info("deleteUser method started");
		logger.info("URI = "+ request.getRequestURI());
		userService.deleteUser(id);
		logger.info("deleteUser method Ended");
		return new ResponseEntity(HttpStatus.OK); 
	}
}
