package com.swadhin.bolg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swadhin.bolg.entities.Comment;
import com.swadhin.bolg.payloads.CommentDto;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>{

	
}
