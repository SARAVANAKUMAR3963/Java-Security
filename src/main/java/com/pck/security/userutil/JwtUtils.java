package com.pck.security.userutil;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private final String SECRET="murugan bbhdfgesryguefhewfhbkeswkbheswdkewfdhefdbhfvvfhesfbhkhjed";
	private final long EXPIRATION_TIME=86400000; // 1 day in milliseconds
	private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	
	 public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.signWith(secretKey,SignatureAlgorithm.HS256)
				.setIssuedAt(new java.util.Date())
				.setExpiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.compact();
	}
	 
	 public String getUserEmailFromToken(String token) {
		 return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	 }
	 
	 public boolean validateToken(String token) {
		 try {
			 getUserEmailFromToken(token);
			 return true;
		 } catch (Exception e) {
			 return false;
		 }
	 }
}
