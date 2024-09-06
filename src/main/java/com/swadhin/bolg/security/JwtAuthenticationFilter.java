package com.swadhin.bolg.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		// 1. get token
		
		String requestToken = request.getHeader("Authorization"); //Bearer gedgehdew3jd03uedj3
		
		
		System.out.println(requestToken);
		
		String username = null;
		
		String token = null;
		
		if (requestToken != null && requestToken.startsWith("Bearer")) {
			
			
			token = requestToken.substring(7); // getting the token
			
			try {
				
				username = this.jwtTokenHelper.getUsernameFromToken(token); // extract the user name
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("Jwt token has expired");
			} catch(MalformedJwtException e) {
				System.out.println("Invalid jwt");
			} catch(Exception e) {
				System.out.println("An Exception occured");
			}
			
		} else {
			System.out.println("Jwt token does not begin with Bearer");
		}
		
		// once we get the token now validate
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if (this.jwtTokenHelper.validateToken(token, userDetails)) { // if token is valid
				
				// sahi chal raha
				// authentication karna hai
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			} else {
				System.out.println("Invalid jwt Token");
			}
			
		} else {
			System.out.println("user name is null and context is not null");
		}
		
		
		filterChain.doFilter(request, response);
		
	}

}