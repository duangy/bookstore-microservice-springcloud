package com.guangyao.bookstore.security.provider;

import com.guangyao.bookstore.domain.security.AuthenticAccountDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private AuthenticAccountDetailsService authenticAccountDetailsService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().toLowerCase();
        String password = (String) authentication.getCredentials();
        UserDetails user = authenticAccountDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) throw new BadCredentialsException("密码不正确");
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
