package com.sample.springsecurity.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;

/**
 * JWT Service - Updated for latest JJWT API (0.12.x)
 * Fixes: Uses SecretKey instead of deprecated string-based signing
 * Improves: Secret key from properties instead of hardcoded
 */
@Service
public class JwtService {
	
	private static final String AUTHORITIES = "authorities";
	private final SecretKey secretKey;
	private final long expirationTime;

	public JwtService(@Value("${jwt.secret:your-secret-key-change-in-production-min-256-bits}") String secret,
	                  @Value("${jwt.expiration:3600000}") long expirationTime) {
		// Fix: Validate secret key length (minimum 256 bits = 32 bytes)
		if (secret == null || secret.length() < 32) {
			throw new IllegalArgumentException(
				"JWT secret must be at least 256 bits (32 characters) long. " +
				"Current length: " + (secret != null ? secret.length() : 0)
			);
		}
		
		// Fix: Use SecretKey for HMAC-SHA algorithms (more secure)
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
		this.expirationTime = expirationTime;
	}

	/**
	 * Create JWT token for user
	 */
	public String createToken(UserDetails userDetails) {
		String username = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationTime);
		
		return Jwts.builder()
				.subject(username)
				.claim(AUTHORITIES, authorities)
				.issuedAt(now)
				.expiration(expiration)
				.signWith(secretKey, Jwts.SIG.HS512) // Fix: Use enum instead of deprecated SignatureAlgorithm
				.compact();
	}

	/**
	 * Check if token has expired
	 */
	public Boolean hasTokenExpired(String token) {
		try {
			Claims claims = extractAllClaims(token);
			return claims.getExpiration().before(new Date());
		} catch (Exception e) {
			// If token cannot be parsed, consider it expired
			return true;
		}
	}

	/**
	 * Validate token against user details
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		try {
			String username = extractUsername(token);
			return userDetails.getUsername().equals(username) && !hasTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Extract username from token
	 */
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	/**
	 * Extract authorities from token
	 */
	@SuppressWarnings("unchecked")
	public Collection<? extends GrantedAuthority> getAuthorities(String token) {
		Claims claims = extractAllClaims(token);
		return (Collection<? extends GrantedAuthority>) claims.get(AUTHORITIES);
	}

	/**
	 * Extract all claims from token
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey) // Fix: Use verifyWith instead of deprecated setSigningKey
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
