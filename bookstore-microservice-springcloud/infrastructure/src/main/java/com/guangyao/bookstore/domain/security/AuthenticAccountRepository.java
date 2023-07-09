package com.guangyao.bookstore.domain.security;

import com.guangyao.bookstore.domain.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticAccountRepository {

    @Autowired
    private AccountServiceClient userService;

    public AuthenticAccount findByUsername(String username) {
        Account account = userService.findByUsername(username);
        return new AuthenticAccount(account);
    }
}
