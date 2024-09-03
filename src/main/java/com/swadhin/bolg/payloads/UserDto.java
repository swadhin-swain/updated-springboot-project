package com.swadhin.bolg.payloads;


import java.util.ArrayList;
import java.util.List;

import com.swadhin.bolg.entities.Comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "User name must be min 4 characters")
	private String name;
	
	@Email(message = "Email addresss is not valid !!")
	private String email;
	
	@NotEmpty
	private String about;
	
	@NotEmpty
	@Size(min = 3,max = 10, message = "password must be min 3 characters and max of 10 characters")
	private String password;
	
	private List<CommentDto> comments = new ArrayList<>();
}
