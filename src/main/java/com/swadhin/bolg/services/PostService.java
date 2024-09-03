package com.swadhin.bolg.services;

import java.util.List;

import com.swadhin.bolg.entities.Post;
import com.swadhin.bolg.payloads.PostDto;
import com.swadhin.bolg.payloads.PostResponse;

public interface PostService {

	
	//create
	
	PostDto createPost(PostDto postDto, Integer userId, Integer catId);
	
	//update 
	PostDto updatePost(PostDto postDto,Integer id);
	
	//delete
	void deletePost(Integer id);
	
	//get post
	//PostDto getPostById(String postId);
	
	//get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
	//get all post by category
	PostResponse getPostsByCategory(Integer catId,Integer pageNumber, Integer pageSize);
	
	//get all post by user
	PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize);
	
	//search post
	List<PostDto> searchPosts(String keyword);

	//get post by id
	PostDto getPostById(Integer id);
}
