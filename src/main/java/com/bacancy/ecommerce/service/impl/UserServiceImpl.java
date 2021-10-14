package com.bacancy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.User;
import com.bacancy.ecommerce.exception.UserNotFoundException;
import com.bacancy.ecommerce.repository.UserRepository;
import com.bacancy.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto addUser(UserDto userDto) {
		logger.info("addUser Method Started");
		
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
		logger.info("User Saved & addUser Method Ended");
		return savedUserDto;
	}

	@Override
	public UserDto getUserById(Long id) {
		logger.info("getUserByID Method Started");
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			logger.error("User Not found By id "+id);
			throw new UserNotFoundException("User not found by id "+id);
		}
		UserDto userDto = modelMapper.map(userOptional.get(), UserDto.class);
		logger.info("getUserById Method Ended");
		return userDto;
	}

	@Override
	public List<UserDto> allUsers() {
		logger.info("allUsers Method Started");
		List<User> users = userRepository.findAll();
		System.out.println(users.get(0).getUserProfile().getId());
		List<UserDto> usersDto = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		logger.info("allUser Method Ended");
		return usersDto;
	}

	@Override
	public void deleteUser(Long id) {
		logger.info("deleteUser Method Started");
		userRepository.deleteById(id);
		logger.info("deleteUser Method Ended");
	}

}
