package com.swadhin.bolg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.swadhin.bolg.entities.User;
import com.swadhin.bolg.entities.UserPrincipal;
import com.swadhin.bolg.exceptions.ResourseNotFoundException;
import com.swadhin.bolg.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//loading user from database by user name
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourseNotFoundException("User", "email: "+username, 0));
		
		return new UserPrincipal(user,userRepo );
		
	
	}

}
