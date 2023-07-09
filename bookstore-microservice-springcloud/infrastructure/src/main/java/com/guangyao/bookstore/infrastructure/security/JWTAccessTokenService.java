package com.guangyao.bookstore.infrastructure.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class JWTAccessTokenService extends DefaultTokenServices {

    @Inject
    public JWTAccessTokenService(JWTAccessToken token, OAuthClientDetailsService clientService,
                                 Optional<AuthenticationManager> authenticationManager) {
        setTokenStore(new JwtTokenStore(token));
        setClientDetailsService(clientService);
        setAuthenticationManager(authenticationManager.orElseGet(() -> {
            OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
            manager.setClientDetailsService(clientService);
            manager.setTokenServices(this);
            return manager;
        }));
        setTokenEnhancer(token);
        setAccessTokenValiditySeconds(60 * 60 * 24 * 365 * 50);
        setRefreshTokenValiditySeconds(60 * 60 * 24 * 15);
        setSupportRefreshToken(true);
        setReuseRefreshToken(true);
    }
}
