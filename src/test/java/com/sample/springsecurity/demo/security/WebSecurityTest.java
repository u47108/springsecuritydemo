package com.sample.springsecurity.demo.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Integration tests for WebSecurity configuration.
 * Migrado a JUnit 5.
 */
@SpringBootTest
@DisplayName("WebSecurity Configuration Tests")
class WebSecurityTest {

	@Autowired(required = false)
	private SecurityFilterChain securityFilterChain;

	@Autowired(required = false)
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("Should load security filter chain")
	void testSecurityFilterChain_Loaded() {
		// Assert
		assertNotNull(securityFilterChain, "SecurityFilterChain should be configured");
	}

	@Test
	@DisplayName("Should load password encoder")
	void testPasswordEncoder_Loaded() {
		// Assert
		assertNotNull(passwordEncoder, "PasswordEncoder should be configured");
		assertTrue(passwordEncoder instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder,
			"PasswordEncoder should be BCryptPasswordEncoder, not NoOpPasswordEncoder");
	}

	@Test
	@DisplayName("Should encode password correctly")
	void testPasswordEncoder_EncodePassword() {
		// Arrange
		String rawPassword = "testpassword123";

		// Act
		String encoded = passwordEncoder.encode(rawPassword);

		// Assert
		assertNotNull(encoded);
		assertFalse(encoded.equals(rawPassword), "Encoded password should be different from raw password");
		assertTrue(encoded.length() > 20, "BCrypt encoded password should be longer than 20 characters");
	}

	@Test
	@DisplayName("Should match password correctly")
	void testPasswordEncoder_MatchPassword() {
		// Arrange
		String rawPassword = "testpassword123";
		String encoded = passwordEncoder.encode(rawPassword);

		// Act
		Boolean matches = passwordEncoder.matches(rawPassword, encoded);

		// Assert
		assertNotNull(matches);
		assertTrue(matches, "Password should match after encoding");
	}

	@Test
	@DisplayName("Should not match wrong password")
	void testPasswordEncoder_MatchWrongPassword() {
		// Arrange
		String rawPassword = "testpassword123";
		String wrongPassword = "wrongpassword";
		String encoded = passwordEncoder.encode(rawPassword);

		// Act
		Boolean matches = passwordEncoder.matches(wrongPassword, encoded);

		// Assert
		assertNotNull(matches);
		assertFalse(matches, "Wrong password should not match");
	}
}

