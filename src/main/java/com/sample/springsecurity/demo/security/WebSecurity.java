package com.sample.springsecurity.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sample.springsecurity.demo.service.MyUserDetailService;

import java.util.Arrays;
import java.util.List;

/**
 * Security Configuration - Updated for Spring Security 6.x
 * Replaces deprecated WebSecurityConfigurerAdapter with SecurityFilterChain
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;

	@Value("${cors.allowed-origins:http://localhost:4200,http://localhost:3000}")
	private String corsAllowedOrigins;

	@Value("${cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
	private String corsAllowedMethods;

	@Value("${cors.allowed-headers:*}")
	private String corsAllowedHeaders;

	@Value("${cors.allow-credentials:true}")
	private boolean corsAllowCredentials;

	/**
	 * Security Filter Chain - Replaces deprecated configure(HttpSecurity)
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// Disable CSRF for stateless JWT authentication
			.csrf(csrf -> csrf.disable())
			
			// Configure CORS
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			
			// Stateless session management for JWT
			.sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			
			// Authorization rules
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login").permitAll()
				.requestMatchers("/database/populate").permitAll()
				.requestMatchers("/actuator/health").permitAll()
				.anyRequest().authenticated()
			)
			
			// Add JWT filter before username/password filter
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			
			// Security headers
			.headers(headers -> headers
				.contentTypeOptions(options -> {})
				.frameOptions(frame -> frame.deny())
				.xssProtection(xss -> xss
					.headerValue(org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
				)
			);

		return http.build();
	}

	/**
	 * CORS Configuration - Configurable from properties
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// Parse allowed origins from properties
		List<String> allowedOrigins = Arrays.asList(corsAllowedOrigins.split(","));
		configuration.setAllowedOrigins(allowedOrigins.stream()
			.map(String::trim)
			.toList()
		);
		
		// Parse allowed methods from properties
		List<String> allowedMethods = Arrays.asList(corsAllowedMethods.split(","));
		configuration.setAllowedMethods(allowedMethods.stream()
			.map(String::trim)
			.toList()
		);
		
		// Allowed headers
		if ("*".equals(corsAllowedHeaders)) {
			configuration.addAllowedHeader("*");
		} else {
			List<String> allowedHeaders = Arrays.asList(corsAllowedHeaders.split(","));
			configuration.setAllowedHeaders(allowedHeaders.stream()
				.map(String::trim)
				.toList()
			);
		}
		
		configuration.setAllowCredentials(corsAllowCredentials);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Password Encoder - Fix: Use BCrypt instead of NoOpPasswordEncoder
	 * BCryptPasswordEncoder is secure and handles password hashing properly
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		// BCrypt with strength 12 (recommended for production)
		return new BCryptPasswordEncoder(12);
	}

	/**
	 * Authentication Manager - Required for authentication
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(myUserDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}
}
