package com.guangyao.bookstore.domain.service;

import com.guangyao.bookstore.domain.Wallet;
import com.guangyao.bookstore.domain.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);

    @Inject
    private WalletRepository repository;

    public void decrease(Integer accountId, Double amount) {
        Wallet wallet = repository.findByAccountId(accountId).orElseGet(() -> repository.save(new Wallet(accountId, 0D)));
        if (wallet.getMoney() > amount) {
            wallet.setMoney(wallet.getMoney() - amount);
            repository.save(wallet);
            log.info("支付成功。用户余额：{}，本次消费：{}", wallet.getMoney(), amount);
        } else {
            throw new RuntimeException("用户余额不足以支付，请先充值");
        }
    }

    public void increase(Integer accountId, Double amount) {}

    public void frozen(Integer accountId, Double amount) {};

    public void thawed(Integer accountId, Double amount) {};
}
