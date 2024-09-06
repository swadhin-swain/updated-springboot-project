package com.swadhin.bolg.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.swadhin.bolg.security.CustomUserDetailService;
import com.swadhin.bolg.security.JwtAuthenticationEntryPoint;
import com.swadhin.bolg.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig {
	
	@Autowired
    private JwtAuthenticationEntryPoint point;
	
	@Autowired
    private JwtAuthenticationFilter filter;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll()
        		.requestMatchers(HttpMethod.GET).permitAll()
        		.requestMatchers(HttpMethod.DELETE).permitAll()
        		.requestMatchers(HttpMethod.POST).permitAll()
        		.requestMatchers(HttpMethod.PUT).permitAll()
        		.requestMatchers("/v3/api-docs").permitAll()
        		.anyRequest().authenticated())
        .httpBasic(httpBasic -> {})
        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider());
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	
	@Bean
    public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(passwordEncoder());
		
		provider.setUserDetailsService(customUserDetailService);
		
		return provider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	
//	@Bean
//	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//	    StrictHttpFirewall firewall = new StrictHttpFirewall();
//	    firewall.setAllowBackSlash(true);
//	    firewall.setAllowSemicolon(true);
//	    firewall.setAllowUrlEncodedDoubleSlash(true); // Allow URLs with double slashes
//	    return firewall;
//	}
//
//	    @Bean
//	    public WebSecurityCustomizer webSecurityCustomizer() {
//	        return (web) -> web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
//	    }
}