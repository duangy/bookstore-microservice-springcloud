package com.guangyao.bookstore.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public class HS256JWTAccessToken extends JWTAccessToken {

    private static final String JWT_TOKEN_SIGNING_PRIVATE_KEY = "xxxx";

    public HS256JWTAccessToken(UserDetailsService userDetailsService) {
        super(userDetailsService);
        setSigningKey(JWT_TOKEN_SIGNING_PRIVATE_KEY);
    }
}
