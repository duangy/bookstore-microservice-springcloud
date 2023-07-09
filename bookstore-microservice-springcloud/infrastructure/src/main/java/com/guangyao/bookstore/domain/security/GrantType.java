package com.guangyao.bookstore.domain.security;

public interface GrantType {

    String PASSWORD = "password";
    String CLIENT_CREDENTIALS = "client_credentials";
    String IMPLICIT = "implicit";
    String AUTHORIZATIONCODE = "authorizationcode";
    String REFRESH_TOKEN = "refresh_token";
}
