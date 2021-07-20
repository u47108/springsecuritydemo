package com.sample.springsecurity.demo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
this is what the user is going to receive from the server
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
}
