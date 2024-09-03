package com.swadhin.bolg.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.swadhin.bolg.exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swadhin.bolg.entities.User;
import com.swadhin.bolg.payloads.UserDto;
import com.swadhin.bolg.repository.UserRepo;
import com.swadhin.bolg.services.UserService;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.repo.save(user);
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.repo.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User"," id ",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.repo.save(user);
		
		UserDto userDto1 = this.userToUserDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {


		User user = this.repo.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User"," id ",userId));
		
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.repo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.repo.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User"," id ",userId));
		
		this.repo.delete(user);
	}
	
	//conversion of userdto to user class
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	//conversion of user to userDto class
		private UserDto userToUserDto(User user) {
			
			UserDto userDto = this.modelMapper.map(user, UserDto.class);
//			userDto.setId(user.getId());
//			userDto.setName(user.getName());
//			userDto.setEmail(user.getEmail());
//			userDto.setAbout(user.getAbout());
//			userDto.setPassword(user.getPassword());
			
			return userDto;
		}

}
