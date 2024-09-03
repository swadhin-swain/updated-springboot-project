package com.swadhin.bolg.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swadhin.bolg.entities.Category;
import com.swadhin.bolg.entities.Post;
import com.swadhin.bolg.entities.User;
import com.swadhin.bolg.exceptions.ResourseNotFoundException;
import com.swadhin.bolg.payloads.PostDto;
import com.swadhin.bolg.payloads.PostResponse;
import com.swadhin.bolg.repository.CategoryRepo;
import com.swadhin.bolg.repository.PostRepo;
import com.swadhin.bolg.repository.UserRepo;
import com.swadhin.bolg.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourseNotFoundException("User", "id", userId));
		
		Category category = this.categoryRepo.findById(catId).orElseThrow(()-> new ResourseNotFoundException("Category", "id", catId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImgName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
		
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		
		Post post = this.postRepo.findById(id).orElseThrow(()->new ResourseNotFoundException("Post", "id", id));
		
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImgName(postDto.getImgName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		 Post post = this.postRepo.findById(id).orElseThrow(()-> new ResourseNotFoundException("Post", "id", id));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer id) {
		Post post = this.postRepo.findById(id).orElseThrow(()->new ResourseNotFoundException("Post", "id", id));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);// create a pageable object

		Page<Post> pageOfPosts = this.postRepo.findAll(p);// pass the pageable object to find the posts in page
		
		List<Post> posts = pageOfPosts.getContent();//convert the page into list
		
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageOfPosts.getNumber());
		postResponse.setPageSize(pageOfPosts.getSize());
		postResponse.setTotalElemnts(pageOfPosts.getTotalElements());
		postResponse.setTotalPages(pageOfPosts.getTotalPages());
		postResponse.setLastPage(pageOfPosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByCategory(Integer catId, Integer pageNumber, Integer pageSize) {


		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourseNotFoundException("category", "id", catId));
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePosts = this.postRepo.findByCategory(cat,pageable);
		
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElemnts(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourseNotFoundException("User", "id", userId));
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePosts = this.postRepo.findByUser(user, p);
		
		List<Post> posts = pagePosts.getContent();

        List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
        PostResponse postResponse = new PostResponse();
        
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElemnts(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	    // Add wildcards to the keyword for partial matching
	    List<Post> posts = this.postRepo.findByTitleLike("%" + keyword + "%");
	    
	    // Convert the list of Post entities to PostDto objects
	    List<PostDto> postDtos = posts.stream()
	        .map(post -> this.modelMapper.map(post, PostDto.class))
	        .collect(Collectors.toList());
	    
	    return postDtos;
	}


	

}
