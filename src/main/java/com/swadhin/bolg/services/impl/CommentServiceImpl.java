package com.swadhin.bolg.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swadhin.bolg.entities.Comment;
import com.swadhin.bolg.entities.Post;
import com.swadhin.bolg.entities.User;
import com.swadhin.bolg.exceptions.ResourseNotFoundException;
import com.swadhin.bolg.payloads.CommentDto;
import com.swadhin.bolg.repository.CommentRepo;
import com.swadhin.bolg.repository.PostRepo;
import com.swadhin.bolg.repository.UserRepo;
import com.swadhin.bolg.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepo commentRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourseNotFoundException("Post", "id", postId));
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User", "id", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		comment.setUser(user);
		
		Comment createComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(createComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourseNotFoundException("Comment", "id", commentId));
		
		this.commentRepo.delete(comment);
	}

	

	

}
