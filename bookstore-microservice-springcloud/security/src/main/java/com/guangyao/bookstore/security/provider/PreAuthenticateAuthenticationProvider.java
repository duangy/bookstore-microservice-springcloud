package com.guangyao.bookstore.security.provider;

import com.guangyao.bookstore.domain.security.AuthenticAccount;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.inject.Named;

@Named
public class PreAuthenticateAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() instanceof UsernamePasswordAuthenticationToken) {
            AuthenticAccount user = (AuthenticAccount) ((UsernamePasswordAuthenticationToken) authentication.getPrincipal()).getPrincipal();
            if (user.isEnabled() && user.isCredentialsNonExpired() && user.isAccountNonExpired() && user.isAccountNonLocked()) {
                return new PreAuthenticatedAuthenticationToken(user, "", user.getAuthorities());
            } else {
                throw new DisabledException("用户状态不正确");
            }
        } else {
            throw new PreAuthenticatedCredentialsNotFoundException("预验证失败，传上来的令牌是怎么来的？");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
