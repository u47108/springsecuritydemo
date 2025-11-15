package com.sample.springsecurity.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDetails implements UserDetails {

  private String username;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> lista = new ArrayList<>();
    lista.add(new SimpleGrantedAuthority("ROLE_SENSEI"));
    lista.add(new SimpleGrantedAuthority("ROLE_PEPE"));
    return lista;
  }

  /**
   * Fix: This is a demo implementation - in production, this should load from database
   * Note: With BCryptPasswordEncoder, passwords are now hashed
   * This method should return the hashed password from database
   */
  @Override
  public String getPassword() {
    // TODO: Load hashed password from database in production
    // For demo purposes, return a placeholder
    // In production, this should query UserRepository to get the hashed password
    return "pass"; // This should be BCrypt hashed password from database
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
