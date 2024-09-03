package com.swadhin.bolg.services;

import com.swadhin.bolg.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId);
	
	void deleteComment(Integer commentId);
}
