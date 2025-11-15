package com.sample.springsecurity.demo.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sample.springsecurity.demo.security.AuthenticationRequest;
import com.sample.springsecurity.demo.security.AuthenticationResponse;
import com.sample.springsecurity.demo.security.JwtService;
import com.sample.springsecurity.demo.service.MyUserDetailService;

/**
 * Unit tests for UserController.
 * Migrado a JUnit 5 con cobertura completa.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
class UserControllerTest {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private MyUserDetailService myUserDetailService;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private UserController userController;

	private AuthenticationRequest validRequest;
	private UserDetails mockUserDetails;
	private String mockToken;

	@BeforeEach
	void setUp() {
		validRequest = AuthenticationRequest.builder()
			.username("testuser")
			.password("password123")
			.build();

		mockUserDetails = org.springframework.security.core.userdetails.User.builder()
			.username("testuser")
			.password("password123")
			.authorities("ROLE_USER")
			.build();

		mockToken = "mock-jwt-token";
	}

	@Test
	@DisplayName("Should authenticate user successfully")
	void testCreateToken_Success() throws Exception {
		// Arrange
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenReturn(null);
		when(myUserDetailService.loadUserByUsername("testuser"))
			.thenReturn(mockUserDetails);
		when(jwtService.createToken(mockUserDetails))
			.thenReturn(mockToken);

		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(validRequest);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getToken());
		assertEquals(mockToken, response.getBody().getToken());

		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(myUserDetailService, times(1)).loadUserByUsername("testuser");
		verify(jwtService, times(1)).createToken(mockUserDetails);
	}

	@Test
	@DisplayName("Should return UNAUTHORIZED for invalid credentials")
	void testCreateToken_BadCredentials() {
		// Arrange
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenThrow(new BadCredentialsException("Invalid credentials"));

		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(validRequest);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertNotNull(response.getBody());

		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(myUserDetailService, never()).loadUserByUsername(any());
		verify(jwtService, never()).createToken(any());
	}

	@Test
	@DisplayName("Should return UNAUTHORIZED for user not found")
	void testCreateToken_UserNotFound() {
		// Arrange
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenReturn(null);
		when(myUserDetailService.loadUserByUsername("testuser"))
			.thenThrow(new UsernameNotFoundException("User not found"));

		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(validRequest);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertNotNull(response.getBody());

		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(myUserDetailService, times(1)).loadUserByUsername("testuser");
		verify(jwtService, never()).createToken(any());
	}

	@Test
	@DisplayName("Should return BAD_REQUEST for null request")
	void testCreateToken_NullRequest() {
		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(null);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		verify(authenticationManager, never()).authenticate(any());
		verify(myUserDetailService, never()).loadUserByUsername(any());
		verify(jwtService, never()).createToken(any());
	}

	@Test
	@DisplayName("Should return BAD_REQUEST for empty username")
	void testCreateToken_EmptyUsername() {
		// Arrange
		AuthenticationRequest request = AuthenticationRequest.builder()
			.username("")
			.password("password123")
			.build();

		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(request);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		verify(authenticationManager, never()).authenticate(any());
		verify(myUserDetailService, never()).loadUserByUsername(any());
		verify(jwtService, never()).createToken(any());
	}

	@Test
	@DisplayName("Should return BAD_REQUEST for null password")
	void testCreateToken_NullPassword() {
		// Arrange
		AuthenticationRequest request = AuthenticationRequest.builder()
			.username("testuser")
			.password(null)
			.build();

		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(request);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		verify(authenticationManager, never()).authenticate(any());
		verify(myUserDetailService, never()).loadUserByUsername(any());
		verify(jwtService, never()).createToken(any());
	}

	@Test
	@DisplayName("Should handle whitespace in username")
	void testCreateToken_WhitespaceUsername() {
		// Arrange
		AuthenticationRequest request = AuthenticationRequest.builder()
			.username("  testuser  ")
			.password("password123")
			.build();

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenReturn(null);
		when(myUserDetailService.loadUserByUsername("testuser"))
			.thenReturn(mockUserDetails);
		when(jwtService.createToken(mockUserDetails))
			.thenReturn(mockToken);

		// Act
		ResponseEntity<AuthenticationResponse> response = userController.createToken(request);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Verify that username was trimmed
		verify(myUserDetailService, times(1)).loadUserByUsername("testuser");
	}

	@Test
	@DisplayName("Should return test string from test endpoint")
	void testTest() {
		// Act
		ResponseEntity<String> response = userController.test();

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("test", response.getBody());
	}
}

