package com.bacancy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.User;
import com.bacancy.ecommerce.repository.UserRepository;
import com.bacancy.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto addUser(UserDto userDto) {
		logger.info("addUser Method Started");
		User user = modelMapper.map(userDto, User.class);
		User savedUser = userRepository.save(user);
		UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
		logger.info("User Saved & addUser Method Ended");
		return savedUserDto;
	}

	@Override
	public UserDto getUserById(Long id) {
		logger.info("getUserById Method Started");
		Optional<User> userOptional = userRepository.findById(id);
		UserDto userDto = null;
		if (userOptional.isPresent()) {
			
			userDto = modelMapper.map(userOptional.get(), UserDto.class);
		}
		logger.info("User get Successfully & getUserById Method Ended");
		return userDto;
	}

	@Override
	public List<UserDto> allUsers() {
		logger.info("allUsers Method Started");
		List<User> users = userRepository.findAll();
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
