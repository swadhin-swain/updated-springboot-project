package com.swadhin.bolg.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swadhin.bolg.entities.Category;
import com.swadhin.bolg.entities.Post;
import com.swadhin.bolg.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	Page<Post> findByUser(User user,Pageable p);
	
	Page<Post> findByCategory(Category category, Pageable p);
	
	@Query(value = "SELECT * FROM post WHERE title LIKE :n", nativeQuery = true)
	List<Post> findByTitleLike(@Param("n") String title);

	

}
