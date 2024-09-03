package com.swadhin.bolg.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swadhin.bolg.config.AppConstants;
import com.swadhin.bolg.payloads.ApiResponse;
import com.swadhin.bolg.payloads.PostDto;
import com.swadhin.bolg.payloads.PostResponse;
import com.swadhin.bolg.services.FileService;
import com.swadhin.bolg.services.PostService;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;

	
	//create post
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(
			@Valid@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer catId) {
		
		PostDto createdPost = this.postService.createPost(postDto, userId, catId);
		
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
		
	}
	
	//get posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
			@PathVariable("userId") Integer id,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NBUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
		
		PostResponse postResponse = this.postService.getPostsByUser(id, pageNumber, pageSize);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//get posts by category
		@GetMapping("/category/{catId}/posts")
		public ResponseEntity<PostResponse> getPostsBycatgory(
				@PathVariable Integer catId,
				@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NBUMBER,required = false) Integer pageNumber,
				@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
			
			PostResponse posts = this.postService.getPostsByCategory(catId, pageNumber, pageSize);
			
			return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
		}
		
		//get post by id
		@GetMapping("/post/{id}")
		public ResponseEntity<PostDto> findPostById(@PathVariable Integer id) {
			
			PostDto postDto = this.postService.getPostById(id);
			
			return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
			
		}
		
		//get all posts
				@GetMapping("/posts")
				public ResponseEntity<PostResponse> findPosts(
						@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NBUMBER,required = false) Integer pageNumber,
						@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
						@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
						@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
						) {
					
					PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
					
					return new ResponseEntity< PostResponse>( postResponse,HttpStatus.OK);
					
				}
		
	 // delete post by id
				@DeleteMapping("/delete/{id}")
				public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id) {
					this.postService.deletePost(id);
					return new ResponseEntity<ApiResponse>(new ApiResponse("Post is successfully deleted",true),HttpStatus.OK);
				}
				
				//update post 
				@PutMapping("/update/{id}")
				public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer id) {
					
					PostDto updatedPost = this.postService.updatePost(postDto, id);
					
					return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
				}
				
				// search
				@GetMapping("/posts/search/{title}")
				public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("title") String keywords) {
					List<PostDto> result = this.postService.searchPosts(keywords);
					
					return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
				}
				
				
				//upload img
				
				@Value("${project.image}")
				private String path;
				
				@PostMapping("/post/image/upload/{postId}")
				public ResponseEntity<PostDto> uploadImg(@RequestParam("image") MultipartFile image,
						@PathVariable Integer postId) throws IOException {
					
					PostDto postDto = this.postService.getPostById(postId);
					
					String fileName = this.fileService.uploadImage(path, image);
					
					
					postDto.setImgName(fileName);
					
					PostDto updatePost = this.postService.updatePost(postDto, postId);
					
					return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
				}
				
				
				//getting image
				@GetMapping("post/image/{imageName}")
				public void downloadImage(
						@PathVariable String imageName,
						HttpServletResponse response
						) throws IOException {
					
					InputStream resource = this.fileService.getResourse(path, imageName);
					response.setContentType(MediaType.IMAGE_JPEG_VALUE);
					StreamUtils.copy(resource, response.getOutputStream());
				}

}
