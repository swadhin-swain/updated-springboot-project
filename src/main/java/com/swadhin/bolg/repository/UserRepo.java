package com.swadhin.bolg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swadhin.bolg.entities.Role;
import com.swadhin.bolg.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	
	Optional<User> findByEmail(String email);
	
	 @Query(value = "SELECT r.name FROM role r " +
             "JOIN user_role ur ON ur.role = r.id " +
             "WHERE ur.user = :id", nativeQuery = true)
List<String> findRolesByUserId(@Param("id") int id);

	

	
}
