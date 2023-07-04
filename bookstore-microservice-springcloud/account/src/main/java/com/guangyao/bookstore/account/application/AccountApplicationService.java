package com.guangyao.bookstore.account.application;

import com.guangyao.bookstore.account.domain.AccountRepository;
import com.guangyao.bookstore.domain.account.Account;
import com.guangyao.bookstore.infrastructure.utility.Encryption;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
@Transactional
public class AccountApplicationService {

    @Inject
    private AccountRepository repository;

    @Inject
    private Encryption encoder;

    public void createAccount(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        repository.save(account);
    }

    public Account findAccountByUsername(String username) { return repository.findByUsername(username); }

    public void updateAccount(Account account) { repository.save(account); }
}
