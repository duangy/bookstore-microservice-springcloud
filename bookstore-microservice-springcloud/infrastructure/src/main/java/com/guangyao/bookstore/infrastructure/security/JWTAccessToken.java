package com.guangyao.bookstore.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class JWTAccessToken extends JwtAccessTokenConverter {

    @Inject
    public JWTAccessToken(UserDetailsService userDetailsService) {
        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(userDetailsService);
        ((DefaultAccessTokenConverter) getAccessTokenConverter()).setUserTokenConverter(converter);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Authentication user = authentication.getUserAuthentication();
        if (user != null) {
            String[] authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
            Map<String, Object> payLoad = new HashMap<>();
            payLoad.put("username", user.getName());
            payLoad.put("authorities", authorities);
            payLoad.put("iss", "test");
            payLoad.put("sub", "bookstore");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(payLoad);
        }
        return super.enhance(accessToken, authentication);
    }
}
