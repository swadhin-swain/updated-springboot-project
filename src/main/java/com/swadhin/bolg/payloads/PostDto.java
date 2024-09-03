package com.swadhin.bolg.payloads;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.swadhin.bolg.entities.Category;
import com.swadhin.bolg.entities.Comment;
import com.swadhin.bolg.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private int id;
	@NotBlank
	@Size(min = 4, message = "title size should not be less than 4")
    private String title;
	
	@NotBlank
	@Size(min = 4, message = "content size should not be less than 4")
	private String content;
	private String imgName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	
	private List<CommentDto> comments = new ArrayList<>();
	
}