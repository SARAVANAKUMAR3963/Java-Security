package com.pck.security.userutil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() throws Exception {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)   // ✅ ADD THIS
		    .httpBasic(AbstractHttpConfigurer::disable) 
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/user/login", "/api/user/addUser").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 
			
		return http.build();
	}
	

}
