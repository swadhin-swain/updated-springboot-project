package com.swadhin.bolg.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swadhin.bolg.payloads.ApiResponse;
import com.swadhin.bolg.payloads.UserDto;
import com.swadhin.bolg.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	//POST - create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid@RequestBody UserDto userDto) {
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	//PUT - update user
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid@RequestBody UserDto userDto,@PathVariable("id") int id) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, id);
		
		return ResponseEntity.ok(updatedUserDto);
		
	}
	
	//DELETE - delete user
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delateUser(@PathVariable("id") int id) {
		this.userService.deleteUser(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
		
	}
	
	//GET - get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//GET - user get
		@GetMapping("/{id}")
		public ResponseEntity<UserDto> getAllUsers(@PathVariable("id") int id) {
			return ResponseEntity.ok(this.userService.getUserById(id));
		}
}
