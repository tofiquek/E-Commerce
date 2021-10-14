package com.bacancy.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bacancy.ecommerce.config.CustomUserDetails;
import com.bacancy.ecommerce.entity.User;
import com.bacancy.ecommerce.exception.UserNotFoundException;
import com.bacancy.ecommerce.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(username);
		if(user == null) {
			throw new UserNotFoundException("Could not found user by email "+username);
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}

}
