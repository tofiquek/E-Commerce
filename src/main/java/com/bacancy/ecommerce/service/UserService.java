package com.bacancy.ecommerce.service;

import java.util.List;

import com.bacancy.ecommerce.dto.UserDto;

public interface UserService {

	/**
	 * Add user
	 * @param user
	 * @return
	 */
	UserDto addUser(UserDto userDto);
	
	/**
	 * Get Existing User By Id
	 * @param email
	 * @return
	 */
	UserDto getUserById(Long id);
	
	/**
	 * To get List of all User
	 * @return
	 */
	List<UserDto> allUsers();
	
	/**
	 * To delete a user by id
	 * @param email
	 */
	void deleteUser(Long id);

	
}
