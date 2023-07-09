package com.guangyao.bookstore.domain.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class AuthenticAccountDetailsService implements UserDetailsService {

    @Inject
    private AuthenticAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(accountRepository.findByUsername(username)).orElseThrow(() ->
                new UsernameNotFoundException("未找到该用户:" + username));
    }
}
