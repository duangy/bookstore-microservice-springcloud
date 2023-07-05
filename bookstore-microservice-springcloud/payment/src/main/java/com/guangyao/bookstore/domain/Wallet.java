package com.guangyao.bookstore.domain;

import javax.persistence.Entity;

@Entity
public class Wallet extends BaseEntity {
    private Double money;

    private Integer accountId;

    public Wallet() {}

    public Wallet(Integer accountId, Double money) {
        setAccountId(accountId);
        setMoney(money);
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
