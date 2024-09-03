package com.swadhin.bolg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swadhin.bolg.payloads.ApiResponse;
import com.swadhin.bolg.payloads.CommentDto;
import com.swadhin.bolg.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	//create comment
	
	@PostMapping("/comment/user/{userId}/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,
			@PathVariable Integer userId, @PathVariable Integer postId
			) {
		
		CommentDto createdComment = this.commentService.createComment(comment, postId, userId);
		
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	
	//delete comment
	
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer id) {
		this.commentService.deleteComment(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is deleted successfully",true),HttpStatus.OK);
	}

}
